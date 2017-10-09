package com.example.jamie.autosearch_test1.model;

/**
 * Created by Jamie on 10/8/2017.
 */
public class DataModel {
    private String id, name, email;
    private String age;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



    public DataModel() {
    }

    public DataModel(String id, String nama) {
        this.id = id;
        this.name = name;

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

    public void setName(String nama) {
        this.name = nama;
    }
}
