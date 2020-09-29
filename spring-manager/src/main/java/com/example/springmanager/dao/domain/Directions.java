package com.example.springmanager.dao.domain;

public enum Directions {
    IT("IT"), LAWYER("LAWYER"), PHYSIC("PHYSIC");

    private final String value;

    Directions(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
