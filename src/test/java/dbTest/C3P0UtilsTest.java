package dbTest;

import com.model.dbutils.C3P0Utils;
import com.model.dbutils.JDBCReadConfigUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @user Administrator
 * @date 2021/4/1
 */
public class C3P0UtilsTest {

    /**
     * 报错：Table 'mydb.test' doesn't exist
     * 解决：数据库连接参数加上nullCatalogMeansCurrent=true
     */
    @Test
    public void testGetConnectionByC3P0(){
        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        Connection connection4 = null;
        try {
            connection1 = C3P0Utils.getConnection();
            connection2 = C3P0Utils.getConnection();
            connection3 = C3P0Utils.getConnection();
            connection4 = C3P0Utils.getConnection();

            System.out.println(connection1);
            System.out.println(connection2);
            System.out.println(connection3);
            System.out.println(connection4);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection1 != null) {
                    connection1.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection2 != null) {
                    connection2.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection3 != null) {
                    connection3.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (connection4 != null) {
                    connection4.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
