package com.model.dbutils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 数据库连接池C3P0
 * C3P0的配置文件必须名为c3p0-config.xml，并且默认目录在编译后项目的classes下，
 * 否则就需要手动加载配置文件：
 * System.setProperty("com.mchange.v2.c3p0.cfg.xml","configs/c3p0-config.xml");
 * 或
 * System.setProperty("com.mchange.v2.c3p0.cfg.xml","classloader:/resources/settings/c3p0-config.xml");
 * 以上代码放在加载 new ComboPooledDataSource(); 之前。
 * <p>
 * 示例代码：
 * Connection connection = C3P0Utils.getConnection();
 *
 * @user 郑超
 * @date 2021/3/31
 */
public class C3P0Utils {

    /**
     * 存储多个数据库连接的集合
     * key：数据库连接，即named-config，默认连接为"none"，即default-config
     */
    private static final Map<String, ComboPooledDataSource> sourceMap = new HashMap<>();

    /**
     * 根据传入的数据库连接名称，返回对应的Connection。
     * 数据库连接名称在c3p0-config.xml中的named-config标签，
     * 如果是默认连接则对应default-config标签。
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        ComboPooledDataSource source;
        String dbName = JDBCReadConfigUtils.getInstance().getDbName();
        if ("".equals(dbName)) {
            if ((source = sourceMap.get("none")) == null) {
                source = new ComboPooledDataSource();
                sourceMap.put("none", source);
            }
        } else {
            if ((source = sourceMap.get(dbName)) == null) {
                source = new ComboPooledDataSource(dbName);
                sourceMap.put(dbName, source);
            }
        }
        return source.getConnection();
    }

}
