package com.model.dao.utils;

import com.model.dbutils.DBCPUtils;
import com.model.dbutils.JDBCUtils;
import com.utils.reflect.ReflectionFieldUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * 封装通用的增删改查方法
 *
 * @user 郑超
 * @date 2021/4/1
 */
public class ExecuteSQLUtils {

    /**
     * 通过事务批量新增数据
     * <p>
     * 注：本方法中获取的Connection连接是通过dbcp数据库连接池的方式
     *
     * @param sql
     * @param args
     * @param number 每一次提交SQL的数量
     * @param <T>
     */
    public <T> void insertBatchSQL(String sql, List<T> args, int number) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBCPUtils.getConnection();
            // 设置事务的隔离级别
            // connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);// 关闭自动提交事务
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.size(); i++) {
                for (Object objects : ReflectionFieldUtils.getBeanByValues(args.get(i))) {
                    preparedStatement.setObject(i + 1, objects);
                }
                // 积攒SQL
                preparedStatement.addBatch();
                // 积攒到一定程度，统一执行一次，并清空之前积攒的SQL
                if ((i + 1) % number == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            // 额外执行一次剩余的SQL
            if (args.size() % number != 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
            connection.commit();// 提交事务
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();// 回滚事务
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } finally {
            JDBCUtils.releaseDB(null, preparedStatement, connection);
        }
    }

    /**
     * 返回某条记录的某一个字段的值 或 一个统计的值(一共有多少条记录等)
     * <p>
     * 注：本方法中获取的Connection连接是通过dbcp数据库连接池的方式
     *
     * @param sql
     * @param args
     * @param <T>
     * @return 注意接受类型
     */
    public <T> T selectForValue(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBCPUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (T) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(resultSet, preparedStatement, connection);
        }

        return null;
    }

    /**
     * 查询多条记录，返回对应的对象的集合
     * 注意：本方法使用BeanUtils.populate()来填充对象t，请保证字段名和属性名一致(起别名)，否则将填充失败
     * <p>
     * 注：本方法中获取的Connection连接是通过dbcp数据库连接池的方式
     *
     * @param clazz 想要查询的类型
     * @param sql
     * @param args  参数
     * @param <T>
     * @return 对象集合
     */
    public <T> List<T> selectEntityPlaceholders(Class<T> clazz, String sql, Object... args) {
        List<T> entityList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DBCPUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            // executeQuery(sql)：报错，应该是将带有占位符的sql重新传入了，改用无参的executeQuery()就行了
            // You have an error in your SQL syntax;
            // check the manual that corresponds to your MySQL server version for the right syntax to use near '? and dept_no = ?' at line 1
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Map<String, Object>> mapList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    // getColumnName() 获得列名
                    // getColumnLabel() 获得列的别名
                    String name = metaData.getColumnLabel(i + 1);
                    Object value = resultSet.getObject(i + 1);
                    map.put(name, value);
                }
                mapList.add(map);
            }

            for (Map<String, Object> map : mapList) {
                T t = clazz.newInstance();
                // 日期类型转换：将java.sql.Date转为java.util.Date
                // 又或者自己重写Convert（Class type,object value）
                ConvertUtils.register(new DateLocaleConverter(), Date.class);
                BeanUtils.populate(t, map);
                entityList.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(resultSet, preparedStatement, connection);
        }

        return entityList;
    }

    /**
     * 通用的查询单条数的据方法，如果查询出多条数据，那就只取第一条，通过占位符的方式执行SQL
     * 注意：本方法使用BeanUtils.populate()来填充对象t，请保证字段名和属性名一致(起别名)，否则将填充失败
     * <p>
     * 注：本方法中获取的Connection连接是通过dbcp数据库连接池的方式
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T selectEntityPlaceholder(Class<T> clazz, String sql, Object... args) {
        List<?> list = selectEntityPlaceholders(clazz, sql, args);
        if (list.size() > 0)
            return (T) list.get(0);

        return null;
    }

    /**
     * 通用的更新方法：包括 INSERT UPDATE DELETE 通过占位符的方式执行SQL
     * 注意：对应的表结构主键必须自增的，并且设有AUTO_INCREMENT属性，否则不能正确返回主键值
     * <p>
     * 需要插入BLOB类型(二进制大对象)的数据必须使用PreparedStatement，请使用本方法
     * 插入Blob类型：preparedStatement.setBlob(1, inputStream);
     * 获得并操作Blob类型：Blob picture = resultSet.getBlob(1);InputStream inputStream = picture.getBinaryStream();
     * <p>
     * 注：本方法中获取的Connection连接是通过dbcp数据库连接池的方式
     *
     * @param sql
     * @param args 填写SQL占位符的可变形参
     * @return 当前操作为新增时，返回的是新增数据的主键值；当前操作为删除和修改是，返回的是影响行数
     */
    public Long updateEntityPlaceholder(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Long i = 0L;
        try {
            connection = DBCPUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int j = 0; j < args.length; j++) {
                preparedStatement.setObject(j + 1, args[j]);
            }

            i = (long) preparedStatement.executeUpdate();
            // 当新增数据时，获得主键
            if (sql.compareToIgnoreCase("insert") == 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next())
                    i = (Long) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, preparedStatement, connection);
        }

        return i;
    }

    /**
     * 通用的更新方法：包括 INSERT UPDATE DELETE
     * 注：本方法中获取的Connection连接不是通过数据库连接池的方式
     * <p>
     * 需要通过占位符传入参数，请使用updateEntityPlaceholder()
     * 需要插入BLOB类型(二进制大对象)的数据必须使用PreparedStatement，请使用updateEntityPlaceholder()
     *
     * @param sql 要执行的SQL语句
     * @return
     * @version 1.0
     */
    public int updateEntity(String sql) {
        int i = -1;
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCUtils.getConnectionDriverManager();
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, statement, connection);
        }
        return i;
    }

    /**
     * 通用的查询单条数的据方法，如果查询出多条数据，那就只取第一条
     * 注：本方法中获取的Connection连接不是通过数据库连接池的方式
     * <p>
     * 需要通过占位符传入参数，请使用selectEntityPlaceholder()
     *
     * @param sql
     * @return 包含一条数据的list
     * @version 1.0
     */
    public List selectEntity(String sql) {
        List list = new ArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnectionDriver();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    list.add(resultSet.getObject(i));
                }
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(resultSet, statement, connection);
        }

        return list;
    }

}
