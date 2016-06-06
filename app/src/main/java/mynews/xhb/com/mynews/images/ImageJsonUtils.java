package mynews.xhb.com.mynews.images;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.beans.ImageBean;
import mynews.xhb.com.mynews.utils.JsonUtils;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ImageJsonUtils {

    /**
     * 将获取到的json转换成图片列表对象
     *
     * @param res
     * @return
     */
    public static List<ImageBean> readJsonImageBeans(String res) {
        List<ImageBean> imageBenas = new ArrayList<ImageBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res).getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                ImageBean imageBean = JsonUtils.deserialize(jo, ImageBean.class);
                imageBenas.add(imageBean);
            }
        } catch (Exception e) {
            Log.e("nate", e.toString());
        }

        return imageBenas;
    }
}
