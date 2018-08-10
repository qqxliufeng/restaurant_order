package com.android.ql.restaurant.application;


import android.support.multidex.MultiDexApplication;

import com.android.ql.restaurant.component.AppComponent;
import com.android.ql.restaurant.component.AppModule;
import com.android.ql.restaurant.component.DaggerAppComponent;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        JPushInterface.init(this);
        CrashReport.initCrashReport(this, "15cb22aa75", false);
    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
