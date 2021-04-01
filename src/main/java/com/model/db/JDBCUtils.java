package com.model.db;

import java.sql.*;
import java.util.Properties;
import java.util.function.BiFunction;

/**
 * 操作数据库连接的工具类
 *
 * @user 郑超
 * @date 2021/3/31
 */
public class JDBCUtils {

    /**
     * 通过反射创建Driver对象，通过Driver的connect方法获取数据库连接
     * <p>
     * 请使用数据库连接池：DBCPUtils或C3P0Utils
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Deprecated
    public static Connection getConnectionDriver() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        return ((Driver) Class.forName(JDBCReadConfigUtils.getInstance().getDriver()).newInstance()).connect(JDBCReadConfigUtils.getInstance().getJdbcUrl(),
                ((BiFunction<String, String, Properties>) (user, password) -> {
                    Properties properties = new Properties();
                    properties.put("user", user);
                    properties.put("password", password);
                    return properties;
                }).apply(JDBCReadConfigUtils.getInstance().getUser(), JDBCReadConfigUtils.getInstance().getPassword()));
    }

    /**
     * 通过DriverManager的getConnection()方法获取数据库连接
     * <p>
     * DataSource接口是JDBC 2.0 API中的新功能.
     * 应用程序不再需要使用Class.forName()显式加载JDBC驱动程序.
     * Class.forName("com.mysql.cj.jdbc.Driver");
     * <p>
     * 请使用数据库连接池：DBCPUtils或C3P0Utils
     *
     * @return
     * @throws SQLException
     */
    @Deprecated
    public static Connection getConnectionDriverManager() throws SQLException {
        return DriverManager.getConnection(JDBCReadConfigUtils.getInstance().getJdbcUrl(), JDBCReadConfigUtils.getInstance().getUser(),
                JDBCReadConfigUtils.getInstance().getPassword());
    }

    /**
     * 关闭数据库连接
     *
     * @param resultSet
     * @param statement
     * @param connection
     * @return 0：释放成功 -1：释放resultSet失败 -2：释放statement失败 -3：释放connection失败
     */
    public static int releaseDB(ResultSet resultSet, Statement statement, Connection connection) {
        int r = 0;

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwable) {
                r = -1;
                throwable.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwable) {
                r = -2;
                throwable.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwable) {
                r = -3;
                throwable.printStackTrace();
            }
        }

        return r;
    }

}
