package com.saurabh.fingregator;

import android.app.Application;

public class MainApp extends Application {

    private static MainApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static MainApp getInstance() {
        return sInstance;
    }
}
