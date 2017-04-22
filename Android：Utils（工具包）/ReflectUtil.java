package org.alex.util;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者：Alex
 * 时间：2017/2/16 16:49
 * 简述：
 */
@SuppressWarnings("all")
public class ReflectUtil {

    /**
     * 得到  对象 对应的 实体类中的  public 类型 成员属性
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.getFields(this);
     *     得到的 是 属于 tv0, tv1, tv2  的 Field
     * </pre>
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Field[] getFields(@NonNull Object obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        return clazz.getFields();
    }

    /**
     * 得到  对象 对应的 实体类中的  所有 成员属性
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.getFields(this);
     *     得到的 是 属于 tv0, tv1, tv2  的 Field
     * </pre>
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Field[] getDeclaredFields(@NonNull Object obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        return clazz.getDeclaredFields();
    }


    /**
     * 得到  对象 对应的 实体类中的  public 类型 成员属性
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.getFields(this, index);
     *     得到的 是 属于 tv0, tv1, tv2  的 Field
     * </pre>
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Field getField(@NonNull Object obj, int index) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Field[] fieldArray = clazz.getFields();
        if (fieldArray == null || fieldArray.length <= index) {
            return null;
        }
        return fieldArray[index];
    }

    /**
     * 得到  对象 对应的 实体类中的  public 类型 成员属性
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.getDeclaredField(this,index);
     *     得到的 是 属于 tv0, tv1, tv2  的 Field
     * </pre>
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Field getDeclaredField(@NonNull Object obj, int index) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Field[] fieldArray = clazz.getDeclaredFields();
        if (fieldArray == null || fieldArray.length <= index) {
            return null;
        }
        return fieldArray[index];
    }


    /**
     * 得到  对象 对应的 实体类中的  所有 方法
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Method[] getDeclaredMethods(@NonNull Object obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        return clazz.getDeclaredMethods();
    }

    /**
     * 得到  对象 对应的 实体类中的  public 类型的 方法
     *
     * @param obj
     * @return
     */
    @NonNull
    public static Method[] getMethods(@NonNull Object obj) {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        return clazz.getMethods();
    }

    /**
     * 获取  obj  里面的， 第 index 个成员属性
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.field2object(this, index);
     *     得到的 是 属于 tv0  对象
     * </pre>
     *
     * @param obj
     * @param index
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> T field2object(@NonNull Object obj, int index) {
        Field[] declaredFieldArray = getDeclaredFields(obj);
        if (declaredFieldArray == null || declaredFieldArray.length <= index) {
            return null;
        }
        Field field = declaredFieldArray[index];
        if (field == null) {
            return null;
        }
        try {
            /*这句 必须加上*/
            field.setAccessible(true);
            Object fieldObject = field.get(obj);
            return (T) fieldObject;
        } catch (IllegalAccessException e) {
            LogTrack.e(e);
        }
        return null;
    }

    /**
     * 通过 obj 对应类 里面的 成员属性， 反射出 该 属性的 对象
     *
     * @param obj
     * @param field
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> T field2object(@NonNull Object obj, @NonNull Field field) {
        if (field == null) {
            return null;
        }
        try {
            /*这句 必须加上*/
            field.setAccessible(true);
            Object fieldObject = field.get(obj);
            return (T) fieldObject;
        } catch (IllegalAccessException e) {
            LogTrack.e(e);
        }
        return null;
    }


    /**
     * 获取  obj  对应的 Class 里面的 第 index  个 属性（是一个CLass）的 某个方法
     * <pre>
     *     假设 MainActivity 有 tv0, tv1, tv2，在 MainActivity 的 onCreate 方法 调用 ReflectUtil.getDeclaredMethod(this, index, methodName, parameterTypes);
     *     得到的 是 属于 tv0, tv1, tv2  的  Method
     * </pre>
     *
     * @param obj
     * @param index
     * @param methodName     方法名
     * @param parameterTypes 参数类型  String.class
     * @return
     */
    @NonNull
    public static Method getDeclaredMethod(Object obj, int index, String methodName, Class<?>... parameterTypes) {
        Field[] declaredFieldArray = getDeclaredFields(obj);
        if (declaredFieldArray == null || declaredFieldArray.length <= index) {
            return null;
        }
        Field field = getDeclaredFields(obj)[index];
        if (field == null) {
            return null;
        }
        try {
            return field.getType().getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            LogTrack.e(e);
        }
        return null;
    }

    /**
     * 通过 file  反射 出 方法
     *
     * @param field
     * @param methodName
     * @param parameterTypes
     * @return
     */
    @NonNull
    public static Method getDeclaredMethod(Field field, String methodName, Class<?>... parameterTypes) {
        if (field == null) {
            return null;
        }
        try {
            Class<?> type = field.getType();
            LogTrack.e(type);
            return field.getType().getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            LogTrack.e(e);
        }
        return null;
    }

    /**
     * 假设 textView.setText();方法 是 私有的
     * 假设 tv0  需要 反射调用 其  私有 方法 - setText，
     *
     * @param method   方法
     * @param receiver tv0 对象
     * @param args     CharSequence 类型的 一个 对象
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> T invoke(Method method, Object receiver, Object... args) {
        if (method == null || receiver == null) {
            return null;
        }
        try {
            method.setAccessible(true);
            return (T) method.invoke(receiver, args);
        } catch (IllegalAccessException e) {
            LogTrack.e(e);
        } catch (InvocationTargetException e) {
            LogTrack.e(e);
        }
        return null;
    }

    @NonNull
    public static <T> T invoke(@NonNull Object object, @NonNull Object receiver, @NonNull String methodName, Class[] clazz, Object[] args) {
        if (object == null || receiver == null) {
            return null;
        }
        Class<?> newClazz = object.getClass();
        try {
            Method declaredMethod = newClazz.getDeclaredMethod(methodName, clazz);
            declaredMethod.setAccessible(true);
            return (T) declaredMethod.invoke(receiver, args);
        } catch (Exception e) {
            LogTrack.e(e);
        }
        return null;
    }

}

