package com.example.springmanager.dao.domain;

public enum Status {

    WAITING, SERVING, READY;
    private String value;

    public String getValue() {
        return this.value;
    }

}
