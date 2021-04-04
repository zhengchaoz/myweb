package com.model.dao.utils;

import com.model.bean.DeptManager;
import com.model.dbutils.C3P0Utils;
import com.model.dbutils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 第三方工具类DBUtils的使用
 * 未实现：ColumnListHandler：将结果集中指定的列的字段值，封装到一个List集合中
 *
 * @user 郑超
 * @date 2021/4/4
 */
public class QueryRunnerSQLUtils {

    private static final QueryRunner queryRunner = new QueryRunner();

    /**
     * 查询单个值
     * ScalarHandler():把结果集转为一个数值（可以是任意基本数据类型和字符串。Date 等）返回
     *
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T queryScalarHandler(String sql, Object... args) {
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

    /**
     * MapListHandler:将结果集转为一个Map的 list
     * Map 对应查询的一条记录：键：SQL 查询的列名（不是列的别名），值：列的值。
     * 而 MapListHandler:返回的多条记录对应的 Map 的集合。
     *
     * @param sql
     * @param args
     * @return
     */
    public static List<Map<String, Object>> queryMapListHandler(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, new MapListHandler(), args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return null;
    }

    /**
     * MapHandler:查询SQL 对应的第一条记录对应的 Map 对象。 键：SQL 查询的列名（不是列的别名），值：列的值。
     *
     * @param sql
     * @param args
     * @return
     */
    public static Map<String, Object> queryMapHandler(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, new MapHandler(), args);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return null;
    }

    /**
     * 查询多条数据
     * BeanListHandler: 把结果集转为一个 List，该 list不为 null,但可能为空集合（size() 方法返回0） 若 SQL
     * 语句的确能查询到记录，List 中存放创建 BeanListHandler 传入的 Class 对象 对应的对象。
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> queryBeanListHandler(Class<T> clazz, String sql, Object... args) {
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

    /**
     * 查询单条数据
     * BeanHandler: 把结果集的第一条记录转为创建 BeanHandler 对象时传入的Class 参数对应的对象
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T queryBeanHandler(Class<T> clazz, String sql, Object... args) {
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

    /**
     * 实现ResultSetHandler接口重写其handle方法，可以自定义返回值
     * <p>
     * 注：本方法仅作为练习，QueryRunner自身已经有多种实现ResultSetHandler接口的工具类型
     *
     * @param sql
     * @param args
     * @return
     */
    @Deprecated
    public static List<DeptManager> queryMyResultHandler(String sql, Object... args) {
        Connection connection = null;
        try {
            connection = C3P0Utils.getConnection();
            return queryRunner.query(connection, sql, args, new MyResultHandler());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            JDBCUtils.releaseDB(null, null, connection);
        }

        return new ArrayList<>();
    }

    @Deprecated
    private static class MyResultHandler implements ResultSetHandler<List<DeptManager>> {

        @Override
        public List<DeptManager> handle(ResultSet resultSet) throws SQLException {
            List<DeptManager> list = new ArrayList<>();
            while (resultSet.next()) {
                long empNo = resultSet.getLong(1);
                String deptNo = resultSet.getString(2);
                Date fromDate = new Date(resultSet.getDate(3).getTime());
                Date toDate = new Date(resultSet.getDate(4).getTime());

                DeptManager deptManager = new DeptManager(empNo, deptNo, fromDate, toDate);
                list.add(deptManager);
            }
            return list;
        }
    }

    /**
     * queryRunner的update方法，适用于：INSERT DELETE UPDATE
     *
     * @param sql
     * @param args
     * @return 默认返回0
     */
    public static int queryRunnerUpdate(String sql, Object... args) {
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
