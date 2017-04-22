package org.alex.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 作者：Alex
 * 时间：2016年09月03日    10:09
 * 简述：
 */
@SuppressWarnings("all")
public class ObjUtil {
    /**
     * 对象为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return (((String) obj).length() == 0);
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Collection) {
            return ((List) obj).isEmpty();
        }
        if (obj.getClass().isArray()) {
            return (Array.getLength(obj) == 0);
        }
        if (obj instanceof Object[]) {
            return (((Object[]) obj).length == 0);
        }
        if (obj instanceof Bitmap) {
            return isBitmapEmpty((Bitmap) obj);
        }
        if (obj instanceof Drawable) {
            return isDrawableEmpty((Drawable) obj);
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).length() <= 0;
        }
        if (obj instanceof JSONArray) {
            return ((JSONArray) obj).length() <= 0;
        }
        if (obj instanceof android.util.SparseArray) {
            return (((android.util.SparseArray) obj).size() == 0);
        }
        if (obj instanceof android.util.SparseIntArray) {
            return (((android.util.SparseIntArray) obj).size() == 0);
        }
        if (obj instanceof android.util.SparseBooleanArray) {
            return (((android.util.SparseBooleanArray) obj).size() == 0);
        }
        if (obj instanceof android.support.v4.util.SimpleArrayMap) {
            return (((android.support.v4.util.SimpleArrayMap) obj).size() == 0);
        }
        if (obj instanceof android.support.v4.util.LongSparseArray) {
            return (((android.support.v4.util.LongSparseArray) obj).size() == 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray) {
                return (((android.util.LongSparseArray) obj).size() <= 0);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof android.util.SparseLongArray) {
                return (((android.util.SparseLongArray) obj).size() == 0);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (obj instanceof android.util.ArraySet) {
                return ((android.util.ArraySet) obj).isEmpty();
            }
        }
        return ((obj.toString()).length() <= 0);
    }

    /**
     * 对象非空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    private static boolean isBitmapEmpty(Bitmap bitmap) {
        if ((bitmap == null) || (bitmap.getHeight() <= 0) || (bitmap.getWidth() <= 0)) {
            return true;
        }
        return false;
    }

    private static boolean isDrawableEmpty(Drawable drawable) {
        if ((drawable == null) || (drawable.getIntrinsicHeight() <= 0) || (drawable.getIntrinsicWidth() <= 0)) {
            return true;
        }
        return false;
    }

    public static String toString(List<?> list, String split) {
        StringBuilder builder = new StringBuilder();
        builder.append("");
        for (int i = 0; (list != null) && (i < list.size()); i++) {
            if (i < (list.size() - 1)) {
                builder.append(list.get(i) + split);
            } else {
                builder.append(list.get(i) + "");
            }
        }
        return builder.toString();
    }

    public static String[] toStringArray(List<?> list) {
        if ((list == null) || (list.size() <= 0)) {
            return new String[0];
        }
        String array[] = new String[list.size()];
        for (int i = 0; (list != null) && (i < list.size()); i++) {
            array[i] = list.get(i) + "";
        }
        return array;
    }

    public static <T> T[] toArray(List<T> list) {
        if ((list == null) || (list.size() <= 0)) {
            return (T[]) new Object[0];
        }
        Object array[] = new Object[list.size()];
        for (int i = 0; (list != null) && (i < list.size()); i++) {
            array[i] = list.get(i) + "";
        }
        return (T[]) array;
    }

    /**
     * 数组 转 List（实质上还是 ArayList）
     *
     * @param t
     * @param <T>
     * @return 返回一个 ArrayList
     */
    public static <T> List<T> toList(T... t) {
        if ((t == null) || (t.length <= 0)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<T>();
        for (int i = 0; (t != null) && (i < t.length); i++) {
            list.add(t[i]);
        }
        return list;
    }
    /**
     * 是java 原生类 类型
     */
    public static boolean isJavaOriginalClass(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        if (obj instanceof Number) {
            return true;
        }
        if (obj instanceof Boolean) {
            return true;
        }
        if (obj instanceof Character) {
            return true;
        }
        if (obj == null) {
            return true;
        }
        return false;
    }

    /**
     * 从 第0个 开始 ，向右取length个（最多length个）
     */
    public static <T> List<T> subList4Start(List<T> list, int length) {
        return subList4StartLength(list, 0, length);
    }

    /**
     * 从 第startIndex个 开始 ，向右取length个（最多length个）
     */
    public static <T> List<T> subList4StartLength(List<T> list, int startIndex, int length) {
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList<>();
        }
        if (list.size() <= startIndex) {
            startIndex = list.size() - 1;
        }
        List<T> subList = new ArrayList<T>();
        for (int i = startIndex; (i < list.size() && i <= length); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 从 最后一个 开始 ，向左取length个（最多length个）
     */
    public static <T> List<T> subList4End(List<T> list, int length) {
        //list.subList()
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList<>();
        }
        List<T> subList = new ArrayList<T>();
        // 0 1 2 3 4 5 6 7 8
        //8 7 6 5
        //9 - 5 >= 4
        for (int i = list.size() - 1; (i >= 0) && (list.size() - i <= length); i--) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 从 第endIndex个 开始 ，向左取length个（最多length个）
     */
    public static <T> List<T> subList4EndLength(List<T> list, int endIndex, int length) {
        //list.subList()
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList<>();
        }
        if (endIndex >= list.size()) {
            endIndex = list.size() - 1;
        }
        List<T> subList = new ArrayList<T>();
        // 0 1 2 3 4 5 6 7 8
        //8 7 6 5
        //8 - 5 >= 4
        for (int i = endIndex; (i >= 0) && (endIndex - i < length); i--) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * copy一个完整的集合，得到一个ArrayList
     */
    public static <T> List<T> subList4Full(List<T> list) {
        if ((list == null) || (list.size() <= 0)) {
            return new ArrayList<>();
        }
        List<T> subList = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 如果 startIndex 大于list.size 得到一个空集合
     * 如果 endIndex 大于list.size ，最多遍历到 list 结尾
     *
     * @param list
     * @param startIndex 下标， 包含
     * @param endIndex   下标， 包含
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
        if ((list == null) || (list.size() <= 0) || (startIndex > endIndex) || (startIndex >= list.size())) {
            return new ArrayList<>();
        }
        List<T> subList = new ArrayList<T>();
        for (int i = startIndex; (i <= endIndex) && (i < list.size() - 1); i++) {
            subList.add(list.get(i));
        }
        return subList;
    }

    /**
     * 将 对象 所有的 非 static l类型 成员变量 置空
     *
     * @param obj
     */
    public static final void setFieldNull(Object obj) {
        if (obj == null) {
            return;
        }
        Field[] declaredFieldArray = obj.getClass().getDeclaredFields();
        declaredFieldArray = (declaredFieldArray == null) ? new Field[0] : declaredFieldArray;
        for (Field field : declaredFieldArray) {
            int modifiers = field.getModifiers();
            field.setAccessible(true);
            if (!Modifier.isStatic(modifiers)) {
                try {
                    field.set(obj, null);
                    //LogTrack.e(field.getName() + " " + field.get(obj) + " " + field.getModifiers() + " " + Modifier.isStatic(field.getModifiers()));
                } catch (IllegalAccessException e) {
                    LogTrack.e(e);
                }
            }
        }
    }


    /**
     * 数组 合并
     *
     * @param first  可以为空
     * @param second 可以为空
     * @param <T>
     * @return
     */
    public static final <T> T[] concat(T[] first, T[] second) {
        if (first == null || first.length <= 0) {
            return second;
        }
        if (second == null || second.length <= 0) {
            return first;
        }
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}

