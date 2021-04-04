package com.model.dao.impl;

import com.model.dao.DAO;
import com.model.dbutils.C3P0Utils;
import com.model.dbutils.JDBCUtils;
import com.utils.reflect.ReflectionGenericUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 操作数据库表的常用方法的实现
 *
 * @user 郑超
 * @date 2021/4/4
 */
public class JdbcDaoImpl<T> implements DAO<T> {

    private QueryRunner queryRunner = null;
    private final Class<T> clazz;

    public JdbcDaoImpl() {
        queryRunner = new QueryRunner();
        clazz = ReflectionGenericUtils.getSuperGenericType(getClass());
    }

    @Override
    public <E> int batch(String sql, int number, Object[]... args) {
        Connection connection = null;
        int i = 0;
        try {
            connection = C3P0Utils.getConnection();
            connection.setAutoCommit(false);
            i = queryRunner.update(connection, sql, (Object) args);
            DbUtils.commitAndCloseQuietly(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            DbUtils.rollbackAndCloseQuietly(connection);
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return i;
    }

    @Override
    public <E> T getForValue(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, new ScalarHandler<T>(), args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return null;
    }

    @Override
    public List<T> getForList(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return null;
    }

    @Override
    public T get(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return null;
    }

    @Override
    public int update(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.update(connection, sql, args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return 0;
    }
}
