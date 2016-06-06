package mynews.xhb.com.mynews.weather.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.net.URLEncoder;
import java.util.List;

import mynews.xhb.com.mynews.beans.WeatherBean;
import mynews.xhb.com.mynews.utils.OkHttpUtils;
import mynews.xhb.com.mynews.utils.Urls;
import mynews.xhb.com.mynews.weather.WeatrherJsonUtils;

/**
 * Created by Administrator on 2016/6/5.
 */
public class WeatherModelImpl implements WeatherModel {

    @Override
    public void loadWeatherData(final String cityName, final LoadWeatherListener listener) {
        try {
            String url = Urls.WEATHER + URLEncoder.encode(cityName, "utf-8");
            OkHttpUtils.ResultCallBack<String> weatherCallBack = new OkHttpUtils.ResultCallBack<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherBean> weatherBeans = WeatrherJsonUtils.getWeatherInfo(response);
                    listener.onSuccess(weatherBeans);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("加载天气数据失败", e);
                }
            };

            OkHttpUtils.get(url, weatherCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("nate", e.toString());
        }
    }

    @Override
    public void loadLocation(Context context, final LoadLocationListener listener) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("nate", "获取位置失败11111111111111111！");
                listener.onFailure("location failure.", null);
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            Log.e("nate", "获取位置失败22222222222222！");
            listener.onFailure("获取位置失败", null);
            return;
        }
        double latitude = location.getLatitude();     //经度
        double longitude = location.getLongitude(); //纬度
        String url = getLocationURL(latitude, longitude);
        OkHttpUtils.ResultCallBack<String> locationCallBack = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                String city = WeatrherJsonUtils.getCity(response);
                if (TextUtils.isEmpty(city)) {
                    Log.e("nate", "获取位置失败");
                    listener.onFailure("获取位置失败", null);
                } else {
                    listener.onSuccess(city);
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                Log.e("nate", "获取位置失败", e);
                listener.onFailure("获取位置失败123", e);
            }
        };
        OkHttpUtils.get(url, locationCallBack);
    }

    private String getLocationURL(double latitude, double longitude) {
        StringBuffer sb = new StringBuffer(Urls.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        Log.d("nate", sb.toString());
        return sb.toString();
    }

    public interface LoadWeatherListener {
        void onSuccess(List<WeatherBean> list);

        void onFailure(String msg, Exception e);
    }

    public interface LoadLocationListener {
        void onSuccess(String cityName);

        void onFailure(String msg, Exception e);
    }
}
