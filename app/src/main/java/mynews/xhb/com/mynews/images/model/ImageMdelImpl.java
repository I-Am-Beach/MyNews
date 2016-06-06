package mynews.xhb.com.mynews.images.model;

import java.util.List;

import mynews.xhb.com.mynews.beans.ImageBean;
import mynews.xhb.com.mynews.images.ImageJsonUtils;
import mynews.xhb.com.mynews.utils.OkHttpUtils;
import mynews.xhb.com.mynews.utils.Urls;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ImageMdelImpl implements ImageModel {

    /**
     * 获取图片列表
     */
    @Override
    public void loadImageList(final OnLoadImageListener listener) {
        String url = Urls.IMAGES_URL;
        OkHttpUtils.ResultCallBack<String> loadImageCallBack = new OkHttpUtils.ResultCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                List<ImageBean> imageBeans = ImageJsonUtils.readJsonImageBeans(response);
                listener.OnSuccess(imageBeans);
            }

            @Override
            public void onFailure(Exception e) {
                listener.OnFailure("加载图片列表失败！", e);
            }
        };
        OkHttpUtils.get(url, loadImageCallBack);

    }

    public interface OnLoadImageListener {
        void OnSuccess(List<ImageBean> list);

        void OnFailure(String msg, Exception e);
    }


}
