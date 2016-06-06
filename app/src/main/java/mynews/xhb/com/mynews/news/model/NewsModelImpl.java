package mynews.xhb.com.mynews.news.model;

import java.util.List;

import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.beans.NewsDetailBean;
import mynews.xhb.com.mynews.news.NewsJsonUtil;
import mynews.xhb.com.mynews.news.widget.NewsFragment;
import mynews.xhb.com.mynews.utils.OkHttpUtils;
import mynews.xhb.com.mynews.utils.Urls;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NewsModelImpl implements NewsModel {

    /**
     * 加载新闻列表
     *
     * @param url
     * @param type
     * @param listener
     */
    @Override
    public void loadNews(String url, final int type, final OnLoadNewsListListener listener) {
        OkHttpUtils.ResultCallBack<String> loadNewsCallBack = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                List<NewsBean> beans = NewsJsonUtil.readJsonNewsBeans(response, getID(type));
                listener.onSuccess(beans);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("加载新闻列表失败", e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallBack);
    }

    /**
     * 加载新闻详情
     *
     * @param id
     * @param listener
     */
    @Override
    public void loadNewsDetail(final String id, final OnLoadNewsDetailListener listener) {
        String url = getDetailUrl(id);
        OkHttpUtils.ResultCallBack<String> loadNewsDetailCallback = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                NewsDetailBean newsDetailBean = NewsJsonUtil.readJsonNewsDetailBeans(response, id);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("加载新闻详情页失败！", e);
            }
        };
        OkHttpUtils.get(url, loadNewsDetailCallback);
    }

    public interface OnLoadNewsListListener {
        void onSuccess(List<NewsBean> list);

        void onFailure(String msg, Exception e);
    }

    public interface OnLoadNewsDetailListener {
        void onSuccess(NewsDetailBean newsDetailBean);

        void onFailure(String msg, Exception e);
    }

    private String getDetailUrl(String id) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(id).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }

    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
}
