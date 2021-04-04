package dbTest;

import com.model.dbutils.DBCPUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @user Administrator
 * @date 2021/4/1
 */
public class DBCPUtilsTest {

    @Test
    public void testGetConnectionByDBCP(){
        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        try {
            connection1 = DBCPUtils.getConnection();
            connection2 = DBCPUtils.getConnection();
            connection3 = DBCPUtils.getConnection();

            System.out.println(connection1);
            System.out.println(connection2);
            System.out.println(connection3);
        } catch (Exception e) {
            e.printStackTrace();
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
        }
    }

}
