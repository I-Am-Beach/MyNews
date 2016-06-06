package mynews.xhb.com.mynews.news.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.news.NewsAdapter;
import mynews.xhb.com.mynews.news.presenter.NewsPresenter;
import mynews.xhb.com.mynews.news.presenter.NewsPresenterImpl;
import mynews.xhb.com.mynews.news.view.NewsView;
import mynews.xhb.com.mynews.utils.Urls;

/**
 * Created by Administrator on 2016/6/1.
 */
public class NewsListFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private List<NewsBean> mData;
    private NewsPresenter mNewsPresenter;

    private int mType = NewsFragment.NEWS_TYPE_TOP;
    private int pageIndex = 0;

    //单例
    public static NewsListFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);//使用setArguments这种方法的好处是：比如在切换横竖屏幕的时候，不会导致数据的丢失
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter = new NewsPresenterImpl(this);
        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        //定义下拉刷新时的颜色变化
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mNewsAdapter = new NewsAdapter(getActivity().getApplicationContext());
        mNewsAdapter.setmOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mNewsAdapter.getItemCount()
                    && mNewsAdapter.isShowFooter()) {//加载更多
                Log.e("nate", "加载更多。。。");
                mNewsPresenter.loadNews(mType, pageIndex + Urls.PAGE_SIZE);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(View v, int position) {
            NewsBean newsBean = mNewsAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra("news", newsBean);
            View ivNews = v.findViewById(R.id.ivNews);
            //设置进入退出的动画
            ActivityOptionsCompat activityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivNews, getString(R.string.ivnews_desc));
            ActivityCompat.startActivity(getActivity(), intent, activityOptionsCompat.toBundle());
        }
    };

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void addNews(List<NewsBean> newsList) {
        mNewsAdapter.setShowFooter(true);
        if (mData == null) {
            mData = new ArrayList<NewsBean>();
        }
        mData.addAll(newsList);
        if (pageIndex == 0) {
            mNewsAdapter.setData(mData);
        } else {
            if (newsList == null || newsList.size() == 0) {
                mNewsAdapter.setShowFooter(false);
            }
            mNewsAdapter.notifyDataSetChanged();
        }
        pageIndex += Urls.PAGE_SIZE;
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        if (pageIndex == 0) {
            mNewsAdapter.setShowFooter(false);
            mNewsAdapter.notifyDataSetChanged();
        }
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_data_fail), Snackbar.LENGTH_SHORT).show();
    }

    //下拉刷新数据时调用
    @Override
    public void onRefresh() {
        pageIndex = 0;
        if (mData != null) {
            mData.clear();
        }
        mNewsPresenter.loadNews(mType, pageIndex);
    }
}
