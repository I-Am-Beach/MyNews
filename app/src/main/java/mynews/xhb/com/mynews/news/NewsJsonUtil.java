package mynews.xhb.com.mynews.news;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import mynews.xhb.com.mynews.beans.NewsBean;
import mynews.xhb.com.mynews.beans.NewsDetailBean;
import mynews.xhb.com.mynews.utils.JsonUtils;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NewsJsonUtil {

    public static List<NewsBean> readJsonNewsBeans(String res, String value) {
        List<NewsBean> beans = new ArrayList<NewsBean>();

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObject.get(value);
            if(jsonElement == null){
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for(int i=1;i<jsonArray.size();i++){
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    NewsBean news = JsonUtils.deserialize(jo, NewsBean.class);
                    beans.add(news);
                }
            }
        } catch (Exception e) {
            Log.e("nate", "readJsonNewsBeans error", e);
            e.printStackTrace();
        }


        return beans;
    }

    public static NewsDetailBean readJsonNewsDetailBeans(String res, String docId) {
        NewsDetailBean newsDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), NewsDetailBean.class);
        } catch (Exception e) {
            Log.e("nae", "readJsonNewsBeans error", e);
        }
        return newsDetailBean;
    }
}
