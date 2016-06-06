package mynews.xhb.com.mynews.weather.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.beans.WeatherBean;
import mynews.xhb.com.mynews.weather.presenter.WeatherPresenter;
import mynews.xhb.com.mynews.weather.presenter.WeatherPresenterImpl;
import mynews.xhb.com.mynews.weather.view.WeatherView;

/**
 * Created by Administrator on 2016/6/5.
 */
public class WeatherFragment extends Fragment implements WeatherView {

    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private ProgressBar mProgressBar;

    private TextView mTvToday;
    private TextView mTvTodayTemperature;
    private TextView mTvTodayWind;
    private TextView mTvTodayWeather;
    private TextView mTvTodayCity;
    private ImageView mIvTodayWeatherImage;
    private WeatherPresenter mWeatherPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherPresenter = new WeatherPresenterImpl(getActivity().getApplicationContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, null);
        mWeatherLayout = (LinearLayout) view.findViewById(R.id.weather_layout);
        mWeatherContentLayout = (LinearLayout) view.findViewById(R.id.weather_content);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mTvToday = (TextView) view.findViewById(R.id.today);
        mTvTodayCity = (TextView) view.findViewById(R.id.city);
        mTvTodayTemperature = (TextView) view.findViewById(R.id.weather_temp);
        mTvTodayWeather = (TextView) view.findViewById(R.id.weather_weather);
        mTvTodayWind = (TextView) view.findViewById(R.id.weather_wind);
        mIvTodayWeatherImage = (ImageView) view.findViewById(R.id.weather_image);

        mWeatherPresenter.loadWeatherData();
        return view;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mTvTodayCity.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTvToday.setText(data);
    }

    @Override
    public void setTempertaure(String tempertaure) {
        mTvTodayTemperature.setText(tempertaure);
    }

    @Override
    public void setWind(String wind) {
        mTvTodayWind.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTvTodayWeather.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mIvTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean> lists) {
        List<View> views = new ArrayList<View>();
        for (WeatherBean weatherBean : lists) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, null, false);
            TextView tvWeek = (TextView) view.findViewById(R.id.item_week);
            TextView tvTemperature = (TextView) view.findViewById(R.id.item_weather_temp);
            TextView tvWind = (TextView) view.findViewById(R.id.item_weather_wind);
            TextView tvWeather = (TextView) view.findViewById(R.id.item_weather_weather);
            ImageView ivWeatherImage = (ImageView) view.findViewById(R.id.item_weather_image);

            tvWeek.setText(weatherBean.getWeek());
            tvTemperature.setText(weatherBean.getTemperature());
            tvWind.setText(weatherBean.getWind());
            tvWeather.setText(weatherBean.getWeather());
            ivWeatherImage.setImageResource(weatherBean.getImageRes());

            mWeatherContentLayout.addView(view);
            views.add(view);
        }
    }

    @Override
    public void showErrorToast(String msg) {
        Snackbar.make(getActivity().findViewById(R.id.drawer_layout), msg, Snackbar.LENGTH_SHORT).show();
    }
}
