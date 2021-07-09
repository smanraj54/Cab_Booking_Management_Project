package com.dal.cabby.pojo;

public class Profile {
    int id;
    String name;

    public Profile(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
