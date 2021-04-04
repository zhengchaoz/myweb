package dbTest;

import com.model.dbutils.JDBCReadConfigUtils;
import org.junit.Test;

/**
 * @user Administrator
 * @date 2021/3/31
 */
public class JDBCReadConfigUtilsTest {

    @Test
    public void testGetJDBCConfig() {
        System.out.println(JDBCReadConfigUtils.getInstance().getDriver());
        System.out.println(JDBCReadConfigUtils.getInstance().getJdbcUrl());
        System.out.println(JDBCReadConfigUtils.getInstance().getUser());
        System.out.println(JDBCReadConfigUtils.getInstance().getPassword());
        System.out.println(JDBCReadConfigUtils.getInstance() == JDBCReadConfigUtils.getInstance());
        System.out.println(JDBCReadConfigUtils.getInstance().equals(JDBCReadConfigUtils.getInstance()));
        System.out.println(JDBCReadConfigUtils.getInstance().getDbName().equals(""));
    }

}
