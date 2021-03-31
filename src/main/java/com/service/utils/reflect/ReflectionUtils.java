package com.service.utils.reflect;

import java.lang.reflect.*;

/**
 * 反射的工具类
 * <p>
 * getSuperGenericType(Class clazz)     通过反射，获得指定Class的父类声明的泛型的第一个参数类型
 * Object invokeMethod(Object o, String name, Class<?>[] paramTypes, Object[] params)   通过反射，调用对应对象的方法，忽略修饰符(private, protected)
 * setFieldValue(T t, String name, Object value)    通过反射，直接设置对象属性的值，忽略修饰符，跳过set方法
 * getFieldValue(T t, String name)  通过反射，直接获得对象属性的值，忽略修饰符，跳过get方法
 *
 * @user 郑超
 * @date 2021/3/30
 */
public class ReflectionUtils {

    /**
     * 通过反射，获得指定Class的父类声明的泛型参数的类型
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     *
     * @param clazz
     * @param index 指定第几个泛型参数，大于泛型参数的总个数或是小于0就返回Object.class
     * @return
     */
    public static Class getSuperClassGenericType(Class clazz, int index) {
        // getGenericSuperclass() 获得带泛型参数的父类Class 如：com.reflect.entity.Creature<java.lang.String>
        // getSuperclass() 获得父类Class 如：class com.reflect.entity.Creature
        Type genericSuperclass = clazz.getGenericSuperclass();
        // 判断Type是不是参数化类型，Type可以是原始类型、参数化类型、数组类型、类型变量和基本类型
        if (!(genericSuperclass instanceof ParameterizedType))
            return Object.class;
        // 获得所有泛型参数的Class
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        // 判断泛型参数的个数是否足够
        if (index >= actualTypeArguments.length || index < 0)
            return Object.class;
        //判断是否是Class类型
        if (!(actualTypeArguments[index] instanceof Class))
            return Object.class;

        return (Class) actualTypeArguments[index];
    }

    /**
     * 通过反射，获得指定Class的父类声明的泛型的第一个参数类型
     * 如: public EmployeeDao extends BaseDao<Employee, String>
     * 将会获得Employee.Class
     *
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> Class<E> getSuperGenericType(Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

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

}
