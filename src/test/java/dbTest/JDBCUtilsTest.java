package dbTest;

import com.model.db.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @user 郑超
 * @date 2021/3/31
 */
public class JDBCUtilsTest {

    @Test
    public void testGetConnectionDriver() {
        Connection driver = null;
        try {
            driver = JDBCUtils.getConnectionDriver();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            try {
                if (driver != null) {
                    driver.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Test
    public void testGetConnectionDriverManager() {
        Connection driverManager = null;
        try {
            driverManager = JDBCUtils.getConnectionDriverManager();
            System.out.println(driverManager);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (driverManager != null) {
                    driverManager.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Test
    public void testReleaseDB(){
        Connection driverManager = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            driverManager = JDBCUtils.getConnectionDriverManager();
            statement = driverManager.createStatement();
            resultSet = statement.getResultSet();
            System.out.println(driverManager);
            System.out.println(statement);
            System.out.println(resultSet);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            int r = JDBCUtils.releaseDB(resultSet, statement, driverManager);
            System.out.println(r);
        }
    }

}
