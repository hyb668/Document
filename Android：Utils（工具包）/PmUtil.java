package org.alex.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import org.alex.model.ParcelableMap;

import java.util.List;

/**
 * 作者：Alex
 * 时间：2016年09月24日
 * 简述：
 */
@SuppressWarnings("rawtypes")
public class PmUtil {
    public static void printW(ParcelableMap map) {
        printParams(map, "w");
    }

    public static void printE(ParcelableMap map) {
        printParams(map, "e");
    }

    private static void printParams(ParcelableMap map, String type) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((map == null) || (map.getKeyList() != null)) {
            return;
        }
        for (int i = 0; i < map.getKeyList().size(); i++) {
            String key = map.getKeyList().get(i);
            Object value = map.getBundle().get(key);
            if (value != null) {
                stringBuilder.append("\nclass = " + value.getClass().getSimpleName() + "｜key = " + key + "｜value = " + value);
            }
        }
        if (ObjUtil.isEmpty(stringBuilder.toString())) {
            return;
        }
        if ("e".equalsIgnoreCase(type)) {
            LogUtil.e(stringBuilder);
        } else if ("w".equalsIgnoreCase(type)) {
            LogUtil.w(stringBuilder);
        }
    }

    public static ParcelableMap getParcelableMap(Intent intent) {
        Bundle intentBundle = intent.getParcelableExtra(ParcelableMap.extraBundle);
        List<String> bundleKeyList = intent.getStringArrayListExtra(ParcelableMap.bundleKey);
        ParcelableMap parcelableMap = new ParcelableMap();
        for (int i = 0; (bundleKeyList != null) && (i < bundleKeyList.size()); i++) {
            String key = bundleKeyList.get(i);
            Object value = intentBundle.get(key);
            if (value instanceof Boolean) {
                parcelableMap.put(key, (Boolean) value);
            } else if (value instanceof String) {
                parcelableMap.put(key, (String) value);
            } else if (value instanceof Integer) {
                parcelableMap.put(key, (Integer) value);
            } else if (value instanceof Double) {
                parcelableMap.put(key, (Double) value);
            } else if (value instanceof Long) {
                parcelableMap.put(key, (Long) value);
            } else if (value instanceof Byte) {
                parcelableMap.put(key, (Byte) value);
            } else if (value instanceof Float) {
                parcelableMap.put(key, (Float) value);
            } else if (value instanceof Parcelable) {
                parcelableMap.put(key, (Parcelable) value);
            }
        }
        return parcelableMap;
    }
}
