package com.passManager;


import com.passManager.database.Database;
import com.passManager.entity.PasswordEntity;
import com.passManager.service.EntityService;
import com.passManager.service.PasswordGenerator;

public class TPMApp {

    public static void main(String[] args) {

        Database database = new Database();
        database.createTable();

        EntityService entityService = new EntityService();


        PasswordEntity entity1 = new PasswordEntity("google4", "google-pass4", 1);
        entityService.createNewEntity(entity1);

        System.out.println(database.getPasswordByField("title","google4"));
//        System.out.println(database.getPasswordByField("site","example.com"));
//        System.out.println(database.getPasswordByField("id","14"));

        System.out.println(PasswordGenerator.decrypt(database.getPasswordByField("title","google4"), "ExampleString@"));


    }


}