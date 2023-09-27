package com.passManager;


import com.passManager.database.Database;
import com.passManager.service.EntityService;

public class TPMApp {

    public static void main(String[] args) {

        Database database = new Database();
        database.createTable();

        EntityService entityService = new EntityService();


//        PasswordEntity entity1 = new PasswordEntity("google2", "google-pass2", 0);
//        entityService.createNewEntity(entity1);

        System.out.println(database.getPasswordByField("title","Example"));
        System.out.println(database.getPasswordByField("site","example.com"));
        System.out.println(database.getPasswordByField("id","aad"));

    }


}