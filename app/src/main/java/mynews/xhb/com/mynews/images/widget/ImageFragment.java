package mynews.xhb.com.mynews.images.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.ImageBean;
import mynews.xhb.com.mynews.images.ImageAdapter;
import mynews.xhb.com.mynews.images.presenter.ImagePresenter;
import mynews.xhb.com.mynews.images.presenter.ImagePresenterImpl;
import mynews.xhb.com.mynews.images.view.ImageView;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ImageFragment extends Fragment implements ImageView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private ImageAdapter mImageAdapter;
    private List<ImageBean> mData;
    private ImagePresenter mImagePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePresenter = new ImagePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.img_swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.img_recycle_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mImageAdapter = new ImageAdapter(getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //不滚动时且滚动到最后一个位置
            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mImageAdapter.getItemCount()) {
                Snackbar.make(getActivity().findViewById(R.id.drawer_layout), getString(R.string.image_load_more), Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    public void addImages(List<ImageBean> list) {
        if (mData == null) {
            mData = new ArrayList<ImageBean>();
        }
        mData.addAll(list);
        mImageAdapter.setData(mData);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        View view = getActivity() == null ? mRecyclerView.getRootView() : getActivity().findViewById(R.id.drawer_layout);
        Snackbar.make(view, getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        if (mData != null) {
            mData.clear();
        }
        mImagePresenter.loadImageList();
    }
}
