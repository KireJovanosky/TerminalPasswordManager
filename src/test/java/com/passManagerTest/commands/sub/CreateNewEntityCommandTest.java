package com.passManagerTest.commands.sub;

import com.passManager.commands.sub.CreateNewEntityCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreateNewEntityCommandTest {

    private CreateNewEntityCommand command;

    @BeforeEach
    public void setUp() {
        command = new CreateNewEntityCommand();
    }

    @Test
    public void testRunWithStrongOption() throws Exception {
        CommandLine cmd = new CommandLine(command);
        int exitCode = cmd.execute("-s=example.com", "-t=TestSite", "--strong", "1");

        assertEquals(0, exitCode);
        assertEquals("example.com", command.getSite());
        assertEquals("TestSite", command.getTitle());
        assertEquals(1, command.isStrong());
        assertNull(command.getCustomPass());
    }

    @Test
    public void testRunWithCustomOption() throws Exception {
        CommandLine cmd = new CommandLine(command);
        int exitCode = cmd.execute("-s=example.com", "-t=TestSite", "-c=custompassword");

        assertEquals(0, exitCode);
        assertEquals("example.com", command.getSite());
        assertEquals("TestSite", command.getTitle());
        assertNull(command.isStrong());
        assertEquals("custompassword", command.getCustomPass());
    }

    @Test
    public void testRunWithNoOptions() throws Exception {
        CommandLine cmd = new CommandLine(command);
        int exitCode = cmd.execute("-s=example.com", "-t=TestSite");

        assertEquals(0, exitCode);
        assertEquals("example.com", command.getSite());
        assertEquals("TestSite", command.getTitle());
        assertNull(command.isStrong());
        assertNull(command.getCustomPass());
    }

    @Test
    public void testRunWithBothStrongAndCustomOptions() throws Exception {
        CommandLine cmd = new CommandLine(command);
        int exitCode = cmd.execute("-s=example.com", "-t=TestSite", "--strong=1", "-c=custompassword");

        assertEquals(1, exitCode); // Expecting an error exit code
        assertEquals("example.com", command.getSite());
        assertEquals("TestSite", command.getTitle());
        assertEquals(1, command.isStrong());
        assertEquals("custompassword", command.getCustomPass());
    }
}

