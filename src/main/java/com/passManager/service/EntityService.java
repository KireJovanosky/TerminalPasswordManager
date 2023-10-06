package com.passManager.service;

import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class EntityService {


    private PasswordGenerator passwordGenerator = new PasswordGenerator();
    private Database database = new Database();

    public void createNewEntity(PasswordEntity passwordEntity) {
        try {
            String site = passwordEntity.getSite();

            String title = passwordEntity.getTitle();
            Integer strong = passwordEntity.isStrong();

            int passwordLength = (strong == 1) ? 16 : 8;
            String password = passwordGenerator.generatePassword(passwordLength);

            database.saveEntity(site, title, strong, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNewEntityWithCustomPass(PasswordEntity passwordEntity) {
        String site = passwordEntity.getSite();
        String title = passwordEntity.getTitle();
        int strong = 2;
        String password = passwordEntity.getPassword();
        if ((password == null) || (password.length()) < 6) {
            System.out.println("Please enter password 6 characters or longer");
        } else {
            String encryptedPassword;
            encryptedPassword = passwordGenerator.userDefinedPassword(password);
            database.saveEntity(site, title, strong, encryptedPassword);
        }
    }

    public String getPasswordEntity(String field, String value) {
        try {
            String encryptedPassword = database.getPasswordByField(field, value);
            if (encryptedPassword != null) {
                String key = PasswordGenerator.setKey256Bit(passwordGenerator.getKeyValue());
                return PasswordGenerator.decrypt(encryptedPassword, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPasswordEntityAndCopyToClipboard(String field, String value) {
        String password = getPasswordEntity(field, value);
        if (password != null) {
            // Copy the password to the clipboard
            copyToClipboard(password);
        }
        return password;
    }

    public void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        System.out.println("Password copied to clipboard.");
    }

    public void deleteEntity (String field, String value) {
        database.deletePasswordByField(field, value);
    }

    public PasswordGenerator getPasswordGenerator() {
        return passwordGenerator;
    }

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
