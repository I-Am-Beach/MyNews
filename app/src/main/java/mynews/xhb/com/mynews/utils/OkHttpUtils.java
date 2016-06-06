package mynews.xhb.com.mynews.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/6/2.
 */
public class OkHttpUtils {
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);

        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mHandler = new Handler(Looper.getMainLooper());
    }

    private synchronized static OkHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }

    private void getRequest(String url, ResultCallBack callBack) {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callBack, request);
    }

    private void postRequest(String url, ResultCallBack callBack, List<Param> params) {
        Request request = builderPostRequest(url, params);
        deliveryResult(callBack, request);
    }

    private void deliveryResult(final ResultCallBack callBack, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallback(callBack, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    if (callBack.type == String.class) {
                        sendSuccessCallback(callBack, str);
                    } else {
                        Object object = JsonUtils.deserialize(str, callBack.type);
                        sendSuccessCallback(callBack, object);
                    }
                } catch (Exception e) {
                    sendFailCallback(callBack, e);
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallBack callBack, final Exception e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallback(final ResultCallBack callBack, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onSuccess(obj);
                }
            }
        });
    }

    private Request builderPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * get请求
     *
     * @param url
     * @param callBack
     */
    public static void get(String url, ResultCallBack callBack) {
        getInstance().getRequest(url, callBack);
    }

    /**
     * post请求
     *
     * @param url
     * @param callBack
     * @param params
     */
    public static void post(String url, ResultCallBack callBack, List<Param> params) {
        getInstance().postRequest(url, callBack, params);
    }

    /**
     * 请求回调类
     *
     * @param <T>
     */
    public static abstract class ResultCallBack<T> {
        Type type;

        public ResultCallBack() {
            type = getSuperClassTypeParameter(getClass());
        }

        static Type getSuperClassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
