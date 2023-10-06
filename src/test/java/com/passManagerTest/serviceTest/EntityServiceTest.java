package com.passManagerTest.serviceTest;

import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;
import com.passManager.service.EntityService;
import com.passManager.service.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertNull;

class EntityServiceTest {

    private EntityService entityService;
    private PasswordGenerator passwordGenerator;
    private Database database;

    @BeforeEach
    void setUp() {
        entityService = new EntityService();
        passwordGenerator = mock(PasswordGenerator.class);
        database = mock(Database.class);
        entityService.setPasswordGenerator(passwordGenerator);
        entityService.setDatabase(database);
        PasswordGenerator.setKey256Bit("ExampleString@");
    }

    @Test
    void createNewEntity_withStrongPassword_shouldSaveEntityWithGeneratedPassword() {
        // Arrange
        PasswordEntity passwordEntity = new PasswordEntity("Example", "example.com", 1);
        String generatedPassword = "generated_password";
        when(passwordGenerator.generatePassword(16)).thenReturn(generatedPassword);

        // Act
        entityService.createNewEntity(passwordEntity);

        // Assert
        verify(database).saveEntity("example.com", "Example", 1, generatedPassword);
    }

    @Test
    void createNewEntity_withWeakPassword_shouldSaveEntityWithGeneratedPassword() {
        // Arrange
        PasswordEntity passwordEntity = new PasswordEntity("Example", "example.com", 0);
        String generatedPassword = "generated_password";
        when(passwordGenerator.generatePassword(8)).thenReturn(generatedPassword);

        // Act
        entityService.createNewEntity(passwordEntity);

        // Assert
        verify(database).saveEntity("example.com", "Example", 0, generatedPassword);
    }

    @Test
    void getPasswordGenerator_shouldReturnSameInstance() {
        // Act
        PasswordGenerator actual = entityService.getPasswordGenerator();

        // Assert
        assertEquals(passwordGenerator, actual);
    }

    @Test
    void setPasswordGenerator_shouldSetNewInstance() {
        // Arrange
        PasswordGenerator newPasswordGenerator = mock(PasswordGenerator.class);

        // Act
        entityService.setPasswordGenerator(newPasswordGenerator);

        // Assert
        assertEquals(newPasswordGenerator, entityService.getPasswordGenerator());
    }

    @Test
    void getDatabase_shouldReturnSameInstance() {
        // Act
        Database actual = entityService.getDatabase();

        // Assert
        assertEquals(database, actual);
    }

    @Test
    void setDatabase_shouldSetNewInstance() {
        // Arrange
        Database newDatabase = mock(Database.class);

        // Act
        entityService.setDatabase(newDatabase);

        // Assert
        assertEquals(newDatabase, entityService.getDatabase());
    }

    @Test
    public void testCreateNewEntityWithCustomPassword() {
        PasswordEntity passwordEntity = new PasswordEntity("Example", "example.com", 2);
        String customPassword = "custom_password";
        passwordEntity.setPassword(customPassword);
        when(passwordGenerator.userDefinedPassword(customPassword)).thenReturn(customPassword);
        entityService.createNewEntityWithCustomPass(passwordEntity);

        // Verify that the entity is saved successfully
        verify(database).saveEntity("example.com", "Example", 2, customPassword);
    }

    @Test
    public void testCreateNewEntityWithInvalidCustomPassword() {
        PasswordEntity passwordEntity = new PasswordEntity("Example", "example.com", 2);
        String customPassword = "short";
        passwordEntity.setPassword(customPassword);
        when(passwordGenerator.userDefinedPassword(customPassword)).thenReturn(customPassword);
        entityService.createNewEntityWithCustomPass(passwordEntity);

        // Expect a message in the console since the password is too short
    }

    @Test
    void getPasswordEntity_invalidFieldAndValue_shouldReturnNull() {
        // Arrange
        String field = "invalid_field";
        String value = "invalid_value";
        when(database.getPasswordByField(field, value)).thenReturn(null);

        // Act
        String result = entityService.getPasswordEntity(field, value);

        // Assert
        assertNull(result);
    }   







}
