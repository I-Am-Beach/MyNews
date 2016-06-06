package mynews.xhb.com.mynews.news.presenter;

import android.util.Log;

import java.util.List;

import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.news.model.NewsModel;
import mynews.xhb.com.mynews.news.model.NewsModelImpl;
import mynews.xhb.com.mynews.news.view.NewsView;
import mynews.xhb.com.mynews.news.widget.NewsFragment;
import mynews.xhb.com.mynews.utils.Urls;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NewsPresenterImpl implements NewsPresenter, NewsModelImpl.OnLoadNewsListListener {

    private NewsView mNewsView;
    private NewsModel mNewsModel;

    public NewsPresenterImpl(NewsView mNewsView) {
        this.mNewsView = mNewsView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNews(int type, int pageiIndex) {
        String url = getUrl(type, pageiIndex);
        Log.v("nate",url);

        if(pageiIndex == 0){
            mNewsView.showProgress();
        }
        mNewsModel.loadNews(url,type,this);
    }

    /**
     * 根据类别和页面索引创建url
     *
     * @param type
     * @param pageIndex
     * @return
     */
    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<NewsBean> list) {
        mNewsView.hideProgress();
        mNewsView.addNews(list);
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsView.hideProgress();
        mNewsView.showLoadFailMsg();
    }
}
