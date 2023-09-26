package com.passManagerTest.entityTest;

import com.passManager.entity.PasswordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordEntityTest {

    private PasswordEntity passwordEntity;

    @BeforeEach
    void setUp() {
        passwordEntity = new PasswordEntity("Example", "example.com", 1);
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Integer id = 123;
        String title = "New Title";
        String site = "newexample.com";
        Integer strong = 0;
        String password = "newpassword";

        // Act
        passwordEntity.setId(id);
        passwordEntity.setTitle(title);
        passwordEntity.setSite(site);
        passwordEntity.setStrong(strong);
        passwordEntity.setPassword(password);

        // Assert
        assertEquals(id, passwordEntity.getId());
        assertEquals(title, passwordEntity.getTitle());
        assertEquals(site, passwordEntity.getSite());
        assertEquals(strong, passwordEntity.isStrong());
        assertEquals(password, passwordEntity.getPassword());
    }
}
