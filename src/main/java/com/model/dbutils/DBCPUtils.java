package com.model.dbutils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * 数据库连接池DBCP
 * <p>
 * 示例代码：
 * Connection connection = DBCPUtils.getConnection();
 *
 * @user 郑超
 * @date 2021/3/31
 */
public class DBCPUtils {

    private static BasicDataSource basicDataSource;

    private static BasicDataSource getBasicDataSource() throws Exception {
        if (basicDataSource == null) {
            basicDataSource = BasicDataSourceFactory.createDataSource(((Supplier<Properties>) () -> {
                InputStream resource = null;
                Properties properties = null;
                try {
                    resource = DBCPUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
                    properties = new Properties();
                    properties.load(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (resource != null) {
                            resource.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return properties;
            }).get());
        }

        return basicDataSource;
    }

    public static Connection getConnection() throws Exception {
        return getBasicDataSource().getConnection();
    }
}
