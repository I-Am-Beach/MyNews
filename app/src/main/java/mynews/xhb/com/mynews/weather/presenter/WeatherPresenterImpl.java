package mynews.xhb.com.mynews.weather.presenter;

import android.content.Context;

import java.util.List;

import mynews.xhb.com.mynews.beans.WeatherBean;
import mynews.xhb.com.mynews.utils.ToolUtils;
import mynews.xhb.com.mynews.weather.model.WeatherModel;
import mynews.xhb.com.mynews.weather.model.WeatherModelImpl;
import mynews.xhb.com.mynews.weather.view.WeatherView;

/**
 * Created by Administrator on 2016/6/5.
 */
public class WeatherPresenterImpl implements WeatherPresenter, WeatherModelImpl.LoadWeatherListener {

    private Context mContext;
    private WeatherView mWeatherView;
    private WeatherModel mWeatherModel;

    public WeatherPresenterImpl(Context mContext, WeatherView mWeatherView) {
        this.mContext = mContext;
        this.mWeatherView = mWeatherView;
        this.mWeatherModel = new WeatherModelImpl();
    }

    @Override
    public void loadWeatherData() {
        mWeatherView.showProgress();

        if (!ToolUtils.isNetworkAvailable(mContext)) {
            mWeatherView.hideProgress();
            mWeatherView.showErrorToast("无网络连接！");
            return;
        }

        WeatherModelImpl.LoadLocationListener locationListener = new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                mWeatherView.setCity(cityName);
                mWeatherModel.loadWeatherData(cityName, WeatherPresenterImpl.this);
            }

            @Override
            public void onFailure(String msg, Exception e) {
                mWeatherView.showErrorToast("定位失败！");
                mWeatherView.setCity("龙岩");
                mWeatherModel.loadWeatherData("龙岩", WeatherPresenterImpl.this);
            }
        };

        //获取定位信息
        mWeatherModel.loadLocation(mContext, locationListener);
    }

    @Override
    public void onSuccess(List<WeatherBean> list) {
        if (list != null && list.size() > 0) {
            WeatherBean todayWeatherBean = list.remove(0);
            mWeatherView.setToday(todayWeatherBean.getDate());
            mWeatherView.setTempertaure(todayWeatherBean.getTemperature());
            mWeatherView.setWeather(todayWeatherBean.getWeather());
            mWeatherView.setWind(todayWeatherBean.getWind());
            mWeatherView.setWeatherImage(todayWeatherBean.getImageRes());
        }

        mWeatherView.setWeatherData(list);
        mWeatherView.hideProgress();
        mWeatherView.showWeatherLayout();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mWeatherView.hideProgress();
        mWeatherView.showErrorToast("获取天气数据失败！");
    }
}
