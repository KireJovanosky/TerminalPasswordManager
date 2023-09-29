package com.passManager.service;

import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;

public class EntityService {


    private PasswordGenerator passwordGenerator = new PasswordGenerator();
    private Database database = new Database();


    public void createNewEntity(PasswordEntity passwordEntity) {
        String site = passwordEntity.getSite();
        String title = passwordEntity.getTitle();
        Integer strong = passwordEntity.isStrong();

        String password;

        if (passwordEntity.isStrong() == 1) {
            password = passwordGenerator.generatePassword(16);
        } else {
            password = passwordGenerator.generatePassword(8);
        }

        database.saveEntity(site, title, strong, password);

    }

    public void CreateNewEntityWithCustomPass(PasswordEntity passwordEntity) {
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
