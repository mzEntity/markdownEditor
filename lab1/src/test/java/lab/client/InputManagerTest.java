package lab.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InputManagerTest {

    @Test
    public void normalTest(){
        InputManager inputManager = new InputManager();
        List<String> result = inputManager.splitInputStr("gcc hello.c -o hello");
        Assertions.assertEquals(result.size(), 4);
        Assertions.assertEquals(result.get(0), "gcc");
        Assertions.assertEquals(result.get(1), "hello.c");
        Assertions.assertEquals(result.get(2), "-o");
        Assertions.assertEquals(result.get(3), "hello");
    }

    @Test
    public void quotationTest(){
        InputManager inputManager = new InputManager();
        List<String> result = inputManager.splitInputStr("insert ## \"hello world!\"");
        Assertions.assertEquals(result.size(), 3);
        Assertions.assertEquals(result.get(0), "insert");
        Assertions.assertEquals(result.get(1), "##");
        Assertions.assertEquals(result.get(2), "hello world!");
    }
}
