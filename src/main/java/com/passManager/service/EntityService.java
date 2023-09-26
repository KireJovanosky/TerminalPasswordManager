package com.passManager.service;

import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;

public class EntityService {


    private PasswordGenerator passwordGenerator = new PasswordGenerator();
    private Database database = new Database();



    public void createNewEntity (PasswordEntity passwordEntity){
        String site = passwordEntity.getSite();
        String title = passwordEntity.getTitle();
        Integer strong = passwordEntity.isStrong();

        if (passwordEntity.isStrong() == 1){
            String password = passwordGenerator.generatePassword(16);
            database.saveEntity(site, title, strong, password);
        }
        String password = passwordGenerator.generatePassword(8);
        database.saveEntity(site, title, strong, password);
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
