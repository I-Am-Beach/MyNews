package mynews.xhb.com.mynews.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import mynews.xhb.com.mynews.R;

/**
 * Created by Administrator on 2016/6/2.
 */
public class ImageLoadUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(placeholder).error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_image_loadfail).crossFade().into(imageView);
    }


}
