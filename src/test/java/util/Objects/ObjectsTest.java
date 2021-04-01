package util.Objects;

import org.junit.Test;

import java.util.Objects;

/**
 * @user Administrator
 * @date 2021/4/1
 */
public class ObjectsTest {

    @Test
    public void testEquals(){
        System.out.println(Objects.equals("a", "a"));
        System.out.println(Objects.equals(null, "a"));
    }

    @Test
    public void testNull(){
        System.out.println(Objects.isNull(""));
        System.out.println(Objects.nonNull(null));
    }

}
