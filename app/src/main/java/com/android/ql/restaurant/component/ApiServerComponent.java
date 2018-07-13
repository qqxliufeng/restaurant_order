package com.android.ql.restaurant.component;



import com.android.ql.restaurant.ui.activity.FragmentContainerActivity;
import com.android.ql.restaurant.ui.activity.SplashActivity;
import com.android.ql.restaurant.ui.fragment.base.BaseNetWorkingFragment;

import dagger.Component;

/**
 * @author Administrator
 * @date 2017/10/16 0016
 */

@ApiServerScope
@Component(modules = {ApiServerModule.class}, dependencies = AppComponent.class)
public interface ApiServerComponent {

    void inject(FragmentContainerActivity activity);

    void inject(BaseNetWorkingFragment baseNetWorkingFragment);

    void inject(SplashActivity splashActivity);

}
