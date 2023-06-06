package com.andorid.finalprj;

import android.app.Application;

import org.xutils.x;

public class HelloWorldApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);

    }
}
