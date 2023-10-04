package com.passManagerTest.commands.sub;

import com.passManager.commands.sub.DeleteEntityCommand;
import com.passManager.service.EntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteEntityCommandTest {

    private DeleteEntityCommand deleteEntityCommand;
    private EntityService entityService;

    @BeforeEach
    void setUp() {
        deleteEntityCommand = new DeleteEntityCommand();
        entityService = mock(EntityService.class);
        deleteEntityCommand.entityService = entityService;
    }

    @Test
    void testDeleteBySite() throws Exception {
        deleteEntityCommand.setSite("example.com");
        deleteEntityCommand.call();

        verify(entityService).deleteEntity("site", "example.com");
    }

    @Test
    void testDeleteByTitle() throws Exception {
        deleteEntityCommand.setTitle("Site Title");
        deleteEntityCommand.call();

        verify(entityService).deleteEntity("title", "Site Title");
    }

    @Test
    void testDeleteById() throws Exception {
        deleteEntityCommand.setId(123);
        deleteEntityCommand.call();

        verify(entityService).deleteEntity("id", "123");
    }

    @Test
    void testInvalidArguments() {
        deleteEntityCommand.setSite("example.com");
        deleteEntityCommand.setTitle("Site Title");
        deleteEntityCommand.setId(123);

        int result = deleteEntityCommand.call();


        verifyNoInteractions(entityService);
        assertEquals(1, result);
    }
}
