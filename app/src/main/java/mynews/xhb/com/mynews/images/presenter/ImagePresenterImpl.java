package mynews.xhb.com.mynews.images.presenter;

import java.util.List;

import mynews.xhb.com.mynews.beans.ImageBean;
import mynews.xhb.com.mynews.images.model.ImageMdelImpl;
import mynews.xhb.com.mynews.images.model.ImageModel;
import mynews.xhb.com.mynews.images.view.ImageView;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ImagePresenterImpl implements ImagePresenter, ImageMdelImpl.OnLoadImageListener {

    private ImageModel mImageModel;
    private ImageView mImageView;

    public ImagePresenterImpl(ImageView mImageView) {
        this.mImageView = mImageView;
        this.mImageModel = new ImageMdelImpl();
    }

    @Override
    public void loadImageList() {
        mImageView.showProgress();
        mImageModel.loadImageList(this);
    }

    @Override
    public void OnSuccess(List<ImageBean> list) {
        mImageView.addImages(list);
        mImageView.hideProgress();
    }

    @Override
    public void OnFailure(String msg, Exception e) {
        mImageView.hideProgress();
        mImageView.showLoadFailMsg();
    }
}
