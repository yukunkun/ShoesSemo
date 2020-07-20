package com.shoesdemo;

import android.app.Application;

import org.litepal.LitePal;

public class BaseApplication extends Application {
    public static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        LitePal.initialize(this);
    }
}
