package com.qewby.network;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testDefaultConstructor() {
        new App();
    }

    @Test
    public void testMain() {
        App.main(new String[] { "" });
    }

    @Test
    public void testApp() {
        assertEquals(App.helloWorld(), "Hello world");
    }
}
