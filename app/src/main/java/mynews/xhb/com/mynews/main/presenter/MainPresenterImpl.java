package mynews.xhb.com.mynews.main.presenter;

import mynews.xhb.com.mynews.R;
import mynews.xhb.com.mynews.main.view.MainView;

/**
 * Created by Administrator on 2016/6/1.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView){
        this.mMainView = mainView;
    }

    @Override
    public void switchNavigation(int id) {
        switch (id){
            case R.id.navigation_item_news:
                mMainView.switch2News();
                break;
            case R.id.navigation_item_images:
                mMainView.switch2Images();
                break;
            case R.id.navigation_item_weather:
                mMainView.switch2Weather();
                break;
            case R.id.navigation_item_about:
                mMainView.switch2About();
                break;
        }
    }
}
