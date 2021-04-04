package com.model.dao;

import java.util.List;

/**
 * 访问数据库的顶级接口
 * 定义了一组常用的操作数据库表的方法
 *
 * @user 郑超
 * @date 2021/4/4
 */
public interface DAO<T> {

    /**
     * 批量处理的方法
     *
     * @param sql
     * @param number
     * @param args
     * @param <E>
     * @return
     */
    <E> int batch(String sql, int number, Object[]... args);

    /**
     * 返回具体的一个值
     *
     * @param sql
     * @param args
     * @param <E>
     * @return
     */
    <E> T getForValue(String sql, Object... args);

    /**
     * 返回 T 的一个集合
     *
     * @param sql
     * @param args
     * @return
     */
    List<T> getForList(String sql, Object... args);

    /**
     * 返回一个 T 对象
     *
     * @param sql
     * @param args
     * @return
     */
    T get(String sql, Object... args);

    /**
     * INSERT, UPDATE, DELETE
     *
     * @param sql
     * @param args
     * @return
     */
    int update(String sql, Object... args);

}
