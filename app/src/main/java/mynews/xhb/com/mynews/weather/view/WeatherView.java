package mynews.xhb.com.mynews.weather.view;

import java.util.List;

import mynews.xhb.com.mynews.beans.WeatherBean;

/**
 * Created by Administrator on 2016/6/5.
 */
public interface WeatherView {
    void showProgress();

    void hideProgress();

    void showWeatherLayout();

    void setCity(String city);

    void setToday(String data);

    void setTempertaure(String tempertaure);

    void setWind(String wind);

    void setWeather(String weather);

    void setWeatherImage(int res);

    void setWeatherData(List<WeatherBean> lists);

    void showErrorToast(String msg);

}
