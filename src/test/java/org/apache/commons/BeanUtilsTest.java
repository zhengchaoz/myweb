package org.apache.commons;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 针对org.apache.commons.beanutils内方法的用法示例
 * <p>
 * copyProperties() 同类之间不同对象要求进行数据复制 / 不同类不同对象之间的数据复制
 *
 * @user 郑超
 * @date 2021/3/31
 */
public class BeanUtilsTest {

    /**
     * describe() 把Bean的属性值放入到一个Map里面
     * populate() 把map里面的值放入bean中
     *
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Test
    public void testDescribeOrPopulate() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserTest user = new UserTest(101, "张三", "123abc");
        Map<String, String> describe = BeanUtils.describe(user);
        System.out.println(describe.toString());

        UserTest userTest = new UserTest();
        BeanUtils.populate(userTest, describe);
        System.out.println(userTest.toString());
    }

    /**
     * setProperty() 设置Bean对象中名称为name的属性值赋值为value.
     * getProperty() 取得bean对象中名为name的属性的值
     *
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    @Test
    public void testGetSetProperty() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserTest user = new UserTest(101, "张三", "123abc");
        String userId = BeanUtils.getProperty(user, "userId");
        System.out.println(userId);

        BeanUtils.setProperty(user, "userName", "李四");
        System.out.println(user.getUserName());
    }

}
