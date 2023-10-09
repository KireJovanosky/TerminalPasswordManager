package com.passManagerTest.serviceTest;

import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;
import com.passManager.service.EntityService;
import com.passManager.service.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class EntityServiceTest {

    private EntityService entityService;
    private PasswordGenerator passwordGenerator;
    private Database database;
    private EntityService entityServiceObj;

    @BeforeEach
    void setUp() {
        entityService = new EntityService();
        passwordGenerator = mock(PasswordGenerator.class);
        database = mock(Database.class);
        entityService.setPasswordGenerator(passwordGenerator);
        entityService.setDatabase(database);
        PasswordGenerator.setKey256Bit("ExampleString@");
        entityServiceObj = new EntityService();
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

    @Test
    void getPasswordEntityTest() {
        // Arrange
        String site = "example.com";
        String title = "Test Site";
        String customPassword = "custompassword";
        int strong = 2;
        String encryptedPassword;
        encryptedPassword = passwordGenerator.userDefinedPassword(customPassword);
        database.saveEntity(site, title, strong, encryptedPassword);

        // Act
        String decryptedPassword = entityServiceObj.getPasswordEntity("title", "TestSite");

        // Assert
        assertEquals("custompassword", decryptedPassword);
    }

    @Test
    void getPasswordEntityTestAndCopyToClipboard() {
        // Arrange
        String site = "example.com";
        String title = "Test Site";
        String customPassword = "example_custom_pass";
        int strong = 2;
        String password = customPassword;
        String encryptedPassword;
        encryptedPassword = passwordGenerator.userDefinedPassword(password);
        database.saveEntity(site, title, strong, encryptedPassword);

        // Act
        String decryptedPassword = entityServiceObj.getPasswordEntityAndCopyToClipboard("title", "TestSite");

        // Assert
        // Pasted password from the clipboard should match: "example_custom_pass"

    }

    @Test
    void getPasswordEntityTestAndCopyToClipboard_PasswordNotFound() {
        // Arrange
        String site = "example.com";
        String title = "Test Site";
        String customPassword = "example_custom_pass";
        int strong = 2;
        String password = customPassword;
        String encryptedPassword;
        encryptedPassword = passwordGenerator.userDefinedPassword(password);
        database.saveEntity(site, title, strong, encryptedPassword);

        // Act
        String decryptedPassword = entityServiceObj.getPasswordEntityAndCopyToClipboard("title", "Non-existent");

        // Assert
        assertNull(decryptedPassword);

    }

    @Test
    void deleteEntity() {
        // Create a test entity in the database
        entityService.createNewEntity(new PasswordEntity("Delete Test Site", "Delete Test Title", 1));

        // Call deleteEntity method
        entityService.deleteEntity("site", "Delete Test Site");

        // Verify that the entity is deleted from the database
        assertNull(entityService.getPasswordEntity("site", "Delete Test Site"));
    }





}
