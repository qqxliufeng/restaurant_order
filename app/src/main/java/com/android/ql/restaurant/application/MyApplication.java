package com.android.ql.restaurant.application;


import android.support.multidex.MultiDexApplication;

import com.android.ql.restaurant.component.AppComponent;
import com.android.ql.restaurant.component.AppModule;
import com.android.ql.restaurant.component.DaggerAppComponent;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
