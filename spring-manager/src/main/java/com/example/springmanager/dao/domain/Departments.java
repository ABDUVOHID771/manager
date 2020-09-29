package com.example.springmanager.dao.domain;

public enum Departments {

    PROGRAMMER("IT"),
    OPERATOR("LAWYER"), LAWYER("LAWYER"), CREDIT_LAWYER("LAWYER"),
    CREDIT_PHYSIC("PHYSIC"), EXCHANGE_MONEY("PHYSIC"), PLASTIC("PHYSIC"), TERMINAL("PHYSIC"), CASHBOX("PHYSIC"), EXCHANGE_CURRENCY("PHYSIC"), OMONAT("PHYSIC");

    private final String value;

    Departments(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
