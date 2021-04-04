package com.utils.reflect;

/**
 * 反射的工具类
 * <p>
 * getSuperGenericType(Class clazz)     通过反射，获得指定Class的父类声明的泛型的第一个参数类型
 * Object invokeMethod(Object o, String name, Class<?>[] paramTypes, Object[] params)   通过反射，调用对应对象的方法，忽略修饰符(private, protected)
 * setFieldValue(T t, String name, Object value)    通过反射，直接设置对象属性的值，忽略修饰符，跳过set方法
 * getFieldValue(T t, String name)  通过反射，直接获得对象属性的值，忽略修饰符，跳过get方法
 *
 * 该类已被废弃，和泛型有关的被转移至ReflectionGenericUtils类，和方法有关的被被转移至ReflectionMethodUtils类，
 * 和字段有关的被转移至ReflectionFieldUtils类
 *
 * @user 郑超
 * @date 2021/3/30
 */
@Deprecated
public class ReflectionUtils {
}
