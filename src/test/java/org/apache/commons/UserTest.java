package org.apache.commons;

/**
 * @user Administrator
 * @date 2021/4/1
 */
public class UserTest {

    private Integer userId;
    private String userName;
    private String Password;

    public UserTest() {
    }

    public UserTest(Integer userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        Password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "UserTest{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
