package com.model.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * 读取JDBC的配置文件
 * <p>
 * 示例代码：
 * JDBCReadConfigUtils.getJDBCConfig().getDriver()     获得驱动
 * JDBCReadConfigUtils.getJDBCConfig().getJdbcUrl()    获得连接
 * JDBCReadConfigUtils.getJDBCConfig().getUser()       获得名称
 * JDBCReadConfigUtils.getJDBCConfig().getPassword()   获得密码
 *
 * @user 郑超
 * @date 2021/3/31
 */
public class JDBCReadConfigUtils {

    private String driver;
    private String jdbcUrl;
    private String user;
    private String password;

    private static JDBCReadConfigUtils jdbcConfig;

    public static JDBCReadConfigUtils getJDBCConfig() {
        if (jdbcConfig == null) {
            jdbcConfig = new JDBCReadConfigUtils();
        }

        return jdbcConfig;
    }

    private JDBCReadConfigUtils() {
        InputStream resource = null;
        try {
            resource = getClass().getClassLoader().getResourceAsStream("jdbc-employees.properties");

            Properties properties = new Properties();
            properties.load(resource);

            driver = properties.getProperty("driver");
            jdbcUrl = properties.getProperty("jdbcUrl");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
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
    }

    public String getDriver() {
        return driver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JDBCReadConfigUtils that = (JDBCReadConfigUtils) o;
        return Objects.equals(driver, that.driver) && Objects.equals(jdbcUrl, that.jdbcUrl) && Objects.equals(user, that.user) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, jdbcUrl, user, password);
    }

    @Override
    public String toString() {
        return "JDBCReadConfigUtils{" +
                "driver='" + driver + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
