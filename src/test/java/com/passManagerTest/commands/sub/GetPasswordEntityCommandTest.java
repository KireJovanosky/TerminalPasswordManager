package com.passManagerTest.commands.sub;

import com.passManager.commands.sub.GetPasswordEntityCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPasswordEntityCommandTest {

    private GetPasswordEntityCommand command;

    @BeforeEach
    public void setUp() {
        command = new GetPasswordEntityCommand();
    }

    @Test
    public void testCallWithValidSite() {
        command.setSite("example.com");
        int result = command.call();
        assertEquals(0, result);
    }

    @Test
    public void testCallWithValidTitle() {
        command.setTitle("Example Site");
        int result = command.call();
        assertEquals(0, result);
    }

    @Test
    public void testCallWithValidId() {
        command.setId(123);
        int result = command.call();
        assertEquals(0, result);
    }

    @Test
    public void testCallWithInvalidOptions() {
        int result = command.call();
        assertEquals(1, result);
    }

    @Test
    public void testCallWithInvalidCombination() {
        command.setSite("example.com");
        command.setTitle("Example Site");
        int result = command.call();
        assertEquals(1, result);
    }

    @Test
    public void testGetSite() {
        command.setSite("example.com");
        String site = command.getSite();
        assertEquals("example.com", site);
    }

    @Test
    public void testSetTitle() {
        command.setTitle("Example Site");
        String title = command.getTitle();
        assertEquals("Example Site", title);
    }

    @Test
    public void testSetId() {
        command.setId(123);
        Integer id = command.getId();
        assertEquals(123, id);
    }
}

