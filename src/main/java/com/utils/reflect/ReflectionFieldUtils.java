package com.utils.reflect;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 有关字段的反射工具类
 * <p>
 * setFieldValue(T t, String name, Object value)    通过反射，直接设置对象属性的值，忽略修饰符，跳过set方法
 * getFieldValue(T t, String name)  通过反射，直接获得对象属性的值，忽略修饰符，跳过get方法
 *
 * @user 郑超
 * @date 2021/4/1
 */
public class ReflectionFieldUtils {

    /**
     * 通过反射，使非公开的变量可以访问
     *
     * @param field
     */
    public static void makeAccessible(Field field) {
        // getModifiers() 获得变量的修饰符
        // 如果不是公开的就设置可访问
        if (!Modifier.isPublic(field.getModifiers()))
            field.setAccessible(true);
    }

    /**
     * 通过反射，循环遍历目标类及其所有父类，寻找目标变量
     *
     * @param o    目标类
     * @param name 变量名
     * @return
     */
    public static Field getDeclaredField(Object o, String name) {
        // getSuperclass() 获得父类Class 如：class com.reflect.entity.Creature
        for (Class<?> supperClass = o.getClass(); supperClass != Object.class; supperClass = supperClass.getSuperclass()) {
            try {
                // getDeclaredFields() 获得当前运行时类的所有变量，包括公开和私有的
                // getFields() 获得当前运行时类及其父类的所有变量，不包括私有的
                return supperClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                // 意味着在当前类中没有找到目标变量，那就去父类找
            }
        }

        return null;
    }

    /**
     * 操作对象变量之前必须要做的一些设置，比如：获取对象变量、校验是否为空、设置变量可访问
     *
     * @param t
     * @param name
     * @param <T>
     * @return
     */
    public static <T> Field fieldNecessitySetting(T t, String name) {
        Field field = getDeclaredField(t, name);

        if (field == null)
            throw new IllegalArgumentException("该类：" + t + " 中没有该变量：" + name);

        makeAccessible(field);

        return field;
    }

    /**
     * 通过反射，直接设置对象属性的值，忽略修饰符，跳过set方法
     *
     * @param t     目标对象
     * @param name  字段名
     * @param value 字段值
     */
    public static <T> void setFieldValue(T t, String name, Object value) {
        try {
            fieldNecessitySetting(t, name).set(t, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射，直接获得对象属性的值，忽略修饰符，跳过get方法
     *
     * @param t    目标对象
     * @param name 字段名
     * @param <T>
     * @return
     */
    public static <T> T getFieldValue(T t, String name) {
        T tt = null;

        try {
            tt = (T) fieldNecessitySetting(t, name).get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return tt;
    }

    /**
     * 利用放射将list中的数据赋值给对象t中的属性，
     * 注意：请保持对象t中属性的个数和顺序和list相同。
     *
     * @param <T>
     * @param t    待赋值的对象
     * @param list
     */
    public static <T> void getBeanByFields(@NotNull T t, @NotNull List list) {
        Field[] declaredFields = t.getClass().getDeclaredFields();

        if (declaredFields.length != list.size())
            throw new IndexOutOfBoundsException("对象中的属性个数和list长度不匹配！");

        for (int i = 0; i < declaredFields.length; i++) {
            try {
                declaredFields[i].setAccessible(true);
                declaredFields[i].set(t, list.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> List<Object> getBeanByValues(@NotNull T t) throws IllegalAccessException {
        List<Object> list = new ArrayList<>();

        Field[] declaredFields = t.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) list.add(declaredField.get(t));

        return list;
    }

}
