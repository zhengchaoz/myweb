package com.utils.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 有关方法的反射工具类
 * <p>
 * Object invokeMethod(Object o, String name, Class<?>[] paramTypes, Object[] params)   通过反射，调用对应对象的方法，忽略修饰符(private, protected)
 *
 * @user 郑超
 * @date 2021/4/1
 */
public class ReflectionMethodUtils {

    /**
     * 通过反射，循环遍历目标类及其所有父类，寻找目标方法
     *
     * @param o          目标类
     * @param name       目标方法的名称
     * @param paramTypes 目标方法的参数，Class数组
     * @return
     */
    public static Method getDeclaredMethod(Object o, String name, Class<?>[] paramTypes) {
        // getSuperclass() 获得父类Class 如：class com.reflect.entity.Creature
        for (Class<?> superClass = o.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                // getDeclaredMethods 获得当前运行时类(superClass)的所有方法，包括公开和私有的
                // getMethods 获得当前运行时类(superClass)及其父类的所有public方法，不包括私有的
                return superClass.getDeclaredMethod(name, paramTypes);
            } catch (NoSuchMethodException e) {
                // 意味着在当前类中没有找到目标方法，那就去父类找
            }
        }
        // 方法不存在
        return null;
    }

    /**
     * 通过反射，调用对应对象的方法，忽略修饰符(private, protected)
     *
     * @param o          目标对象
     * @param name       目标方法的名称
     * @param paramTypes 目标方法的参数类型列表
     * @param params     目标方法的参数值
     * @return
     */
    public static Object invokeMethod(Object o, String name, Class<?>[] paramTypes, Object[] params) {
        Method method = getDeclaredMethod(o, name, paramTypes);
        //　判断是否有该方法
        if (method == null)
            throw new IllegalArgumentException("该类：" + o + " 中没有该方法：" + name);
        // 设置非public方法可以被访问
        method.setAccessible(true);
        // 调用方法
        try {
            return method.invoke(o, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
