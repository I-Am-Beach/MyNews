package mynews.xhb.com.mynews.news.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class NewsFragment extends Fragment {

    public static final int NEWS_TYPE_TOP = 0;//头条
    public static final int NEWS_TYPE_NBA = 1;//NBA
    public static final int NEWS_TYPE_CARS = 2;//汽车
    public static final int NEWS_TYPE_JOKES = 3;//笑话

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);//设置viewpager保留多少个显示界面（解决预加载问题）
        setupViewPager(mViewPager);
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.top));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.nba));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.cars));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.jokes));
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP),getString(R.string.top));
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA),getString(R.string.nba));
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS),getString(R.string.cars));
        myPagerAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES),getString(R.string.jokes));
        viewPager.setAdapter(myPagerAdapter);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment,String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
