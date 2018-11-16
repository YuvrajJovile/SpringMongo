package com.mongo.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class UsersDao implements Serializable {

    @Id
    private Integer id;
    private String name;
    private String teamname;
    private Double salary;

    public UsersDao() {
    }

    public UsersDao(Integer id, String name, String teamname, Double salary) {
        this.id = id;
        this.name = name;
        this.teamname = teamname;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
