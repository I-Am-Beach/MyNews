package mynews.xhb.com.mynews.news.presenter;

import android.content.Context;

import mynews.xhb.com.mynews.beans.NewsDetailBean;
import mynews.xhb.com.mynews.news.model.NewsModel;
import mynews.xhb.com.mynews.news.model.NewsModelImpl;
import mynews.xhb.com.mynews.news.view.NewsDetailView;

/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsDetailPresenterImpl implements NewsDetailPresenter, NewsModelImpl.OnLoadNewsDetailListener {

    private Context mContext;
    private NewsDetailView mNewsDetailView;
    private NewsModel mNewsModel;

    public NewsDetailPresenterImpl(Context mContext, NewsDetailView mNewsDetailView) {
        this.mContext = mContext;
        this.mNewsDetailView = mNewsDetailView;
        this.mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(String id) {
        mNewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(id, this);
    }

    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mNewsDetailView.showNewsDetailContent(newsDetailBean.getBody());
        }
        mNewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();
    }
}
