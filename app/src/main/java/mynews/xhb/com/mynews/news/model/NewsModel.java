package mynews.xhb.com.mynews.news.model;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface NewsModel {
    void loadNews(String url, int type, NewsModelImpl.OnLoadNewsListListener listener);

    void loadNewsDetail(String id, NewsModelImpl.OnLoadNewsDetailListener listener);
}
