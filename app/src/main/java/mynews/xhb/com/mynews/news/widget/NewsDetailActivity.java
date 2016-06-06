package mynews.xhb.com.mynews.news.widget;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.news.presenter.NewsDetailPresenter;
import mynews.xhb.com.mynews.news.presenter.NewsDetailPresenterImpl;
import mynews.xhb.com.mynews.news.view.NewsDetailView;
import mynews.xhb.com.mynews.utils.ImageLoadUtils;
import mynews.xhb.com.mynews.utils.ToolUtils;

/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsDetailActivity extends SwipeBackActivity implements NewsDetailView {

    private NewsBean mNewsBean;
    private HtmlTextView mHtmlTextView;
    private ProgressBar mProgressBar;
    private NewsDetailPresenter mNewsDetailPresenter;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mHtmlTextView = (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeBackLayout = getSwipeBackLayout();
        //这个滑动删除的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用setEdgeSize
        mSwipeBackLayout.setEdgeSize(ToolUtils.getWidthInPx(this));
        //设置滑动方向
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mNewsBean = (NewsBean) getIntent().getSerializableExtra("news");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mNewsBean.getTitle());
        ImageLoadUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mNewsBean.getImgsrc());

        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        mNewsDetailPresenter.loadNewsDetail(mNewsBean.getDocid());
    }

    @Override
    public void showNewsDetailContent(String newsDetailContent) {
        mHtmlTextView.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
