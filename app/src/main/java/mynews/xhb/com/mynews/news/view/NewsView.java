package mynews.xhb.com.mynews.news.view;

import java.util.List;

import mynews.xhb.com.mynews.beans.NewsBean;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface NewsView {
    void showProgress();

    void addNews(List<NewsBean> newsList);

    void hideProgress();

    void showLoadFailMsg();
}
