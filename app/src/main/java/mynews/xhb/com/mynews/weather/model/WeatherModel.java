package mynews.xhb.com.mynews.weather.model;

import android.content.Context;

/**
 * Created by Administrator on 2016/6/5.
 */
public interface WeatherModel {
    void loadWeatherData(String cityName,WeatherModelImpl.LoadWeatherListener listener);

    void loadLocation(Context contextm,WeatherModelImpl.LoadLocationListener listener);
}
