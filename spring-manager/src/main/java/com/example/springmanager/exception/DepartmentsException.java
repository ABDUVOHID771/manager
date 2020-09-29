package com.example.springmanager.exception;

public class DepartmentsException extends BaseException {
    public DepartmentsException(String department) {
        super("Department : " + department + " does not have in this direction !");
    }
}
