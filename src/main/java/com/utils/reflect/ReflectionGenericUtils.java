package com.utils.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 有关泛型的反射工具类
 * <p>
 * getSuperGenericType(Class clazz)     通过反射，获得指定Class的父类声明的泛型的第一个参数类型
 *
 * @user 郑超
 * @date 2021/4/1
 */
public class ReflectionGenericUtils {

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

}
