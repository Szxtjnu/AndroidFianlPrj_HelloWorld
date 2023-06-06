package com.andorid.finalprj.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.andorid.finalprj.SplashActivity;
import com.andorid.finalprj.activity.GuideActivity;

/**
 * 缓存软件的一些参数和数据
 */
public class CacheUtils {
    /**
     * 得到缓存值
     *
     * @param context 上下文
     * @param key     值
     * @return 返回状态
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("first_enter", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 保存软件的参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("first_enter", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 缓存加载过的数据
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("first_enter", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("first_enter", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
}
