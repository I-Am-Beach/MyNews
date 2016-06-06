package mynews.xhb.com.mynews.images.view;

import java.util.List;

import mynews.xhb.com.mynews.beans.ImageBean;

/**
 * Created by Administrator on 2016/6/5.
 */
public interface ImageView {
    void addImages(List<ImageBean> list);

    void showProgress();

    void hideProgress();

    void showLoadFailMsg();
}
