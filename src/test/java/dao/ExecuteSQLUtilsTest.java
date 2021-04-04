package dao;

import com.model.bean.DeptManager;
import com.model.dao.utils.ExecuteSQLUtils;
import com.utils.reflect.ReflectionFieldUtils;
import org.junit.Test;

import java.util.List;

/**
 * @user 郑超
 * @date 2021/4/2
 */
public class ExecuteSQLUtilsTest {

    ExecuteSQLUtils executeSQLUtils = new ExecuteSQLUtils();

    @Test
    public void testSelectForValue(){
        String sql = "select count(emp_no) as `count` from dept_manager where dept_no = ?;";
        Object value = executeSQLUtils.selectForValue(sql, "d001");
        System.out.println((Long) value);
    }

    @Test
    public void testSelectEntityPlaceholders() throws IllegalAccessException, InstantiationException {
        String sql = "select emp_no empNo, dept_no deptNo, from_date fromDate, to_date toDate from dept_manager where dept_no = ?;";
        List<DeptManager> deptManagers = executeSQLUtils.selectEntityPlaceholders(DeptManager.class, sql, "d001");
        for (DeptManager deptManager : deptManagers) {
            System.out.println(deptManager.toString());
        }
    }

    @Test
    public void testSelectEntityPlaceholder(){
        String sql = "select emp_no empNo, dept_no deptNo, from_date fromDate, to_date toDate from dept_manager where emp_no = ? and dept_no = ?;";
        DeptManager deptManager = executeSQLUtils.selectEntityPlaceholder(DeptManager.class, sql, "111534", "d001");
        assert deptManager != null;
        System.out.println(deptManager.toString());
    }

    @Test
    public void testUpdateEntityPlaceholder() {
        String sql = "update dept_manager set from_date = ?, to_date = ? where emp_no = ? and dept_no = ?;";
        Long i = executeSQLUtils.updateEntityPlaceholder(sql, "2019-4-2", "2019-11-29", "111534", "d001");
        System.out.println(i);
    }

    @Test
    public void testUpdateEntity() {
        String sql = "insert into dept_manager (emp_no, dept_no, from_date, to_date) values ('111534', 'd001', '2021-4-2', '2021-3-2');";
        int i = executeSQLUtils.updateEntity(sql);
        System.out.println(i);
    }

    @Test
    public void testSelectEntity() {
        String sql = "select * from dept_manager where emp_no = '111534' and dept_no = 'd001'";
        DeptManager deptManager = new DeptManager();
        ReflectionFieldUtils.getBeanByFields(deptManager, executeSQLUtils.selectEntity(sql));
        System.out.println(deptManager.toString());
    }

}
