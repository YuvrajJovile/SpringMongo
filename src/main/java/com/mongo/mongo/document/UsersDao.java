package com.mongo.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "user_db")
public class UsersDao implements Serializable {

    @Id
    private String id;
    private String name;
    private String userType;


    public UsersDao() {
    }

    public UsersDao(String id, String name, String userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
