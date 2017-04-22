package org.alex.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:12
 * 简述：
 */

@SuppressWarnings("all")
public class SpUtil {
    private static String FILE_NAME = "DefaultSharedPreferences";

    public static void init(String name) {
        SpUtil.FILE_NAME = name;
    }

    public static void fileName(String fileName) {
        SpUtil.FILE_NAME = fileName;
    }

    private static SharedPreferences sharedPreferences() {
        if (BaseUtil.isAppNull()) {
            return null;
        }
        SharedPreferences sharedPreferences = BaseUtil.app().getSharedPreferences(FILE_NAME, Context.MODE_MULTI_PROCESS);
        return sharedPreferences;
    }

    /**
     * 存入String数据
     * <br/>失败, 返回false
     *
     * @param key   键, !null, !""
     * @param value 值
     */
    public static boolean putString(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * 取出String, 失败, 返回null
     *
     * @param key 键, !null, !""
     */
    public static String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return getString(key, null);
    }

    /**
     * 取出String, 失败, 返回 defaultValue
     *
     * @param key 键, !null, !""
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 存入int, 失败, 返回, false
     *
     * @param key 键, !null, !""
     */
    public static boolean putInt(String key, int value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 取出int,  失败, 返回,  -2^31
     *
     * @param key 键, !null, !""
     */
    public static int getInt(String key) {
        if (TextUtils.isEmpty(key)) {
            return Integer.MIN_VALUE;
        }
        return getInt(key, -1);
    }

    /**
     * 取出int,  失败, 返回,  defaultValue
     *
     * @param key 键, !null, !""
     */
    public static int getInt(String key, int defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return defaultValue;
        }
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 存入long,  失败, 返回,  false
     *
     * @param key 键, !null, !""
     */
    public static boolean putLong(String key, long value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * 取出long,  失败, 返回,   - 2^63
     *
     * @param key 键, !null, !""
     */
    public static long getLong(String key) {
        if (TextUtils.isEmpty(key)) {
            return Long.MIN_VALUE;
        }
        return getLong(key, Long.MIN_VALUE);
    }

    /**
     * 取出long,  失败, 返回,   defaultValue
     *
     * @param key 键, !null, !""
     */
    public static long getLong(String key, long defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return defaultValue;
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 存入float,  失败, 返回,  false
     *
     * @param key 键, !null, !""
     */
    public static boolean putFloat(String key, float value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 取出float,  失败, 返回,   2^-149
     *
     * @param key 键, !null, !""
     */
    public static float getFloat(String key) {
        if (TextUtils.isEmpty(key)) {
            return Float.MIN_VALUE;
        }
        return getFloat(key, Float.MIN_VALUE);
    }

    /**
     * 取出float,  失败, 返回,   defaultValue
     *
     * @param key 键, !null, !""
     */
    public static float getFloat(String key, float defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return defaultValue;
        }
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * 存入boolean,  失败, 返回,  false
     *
     * @param key 键, !null, !""
     */
    public static boolean putBoolean(String key, boolean value) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 取出boolean,  失败, 返回,   false
     *
     * @param key 键, !null, !""
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * 取出boolean,  失败, 返回,   defaultValue
     *
     * @param key 键, !null, !""
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        if (TextUtils.isEmpty(key)) {
            return defaultValue;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 移除键值对, 失败, 返回false
     *
     * @param key 键, !null, !""
     */
    public static boolean remove(String key) {
        if (TextUtils.isEmpty(key)) {
            return false;
        }
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor.remove(key).commit();
    }

    public static boolean clear() {
        SharedPreferences sharedPreferences = sharedPreferences();
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor.clear().commit();
    }

}

