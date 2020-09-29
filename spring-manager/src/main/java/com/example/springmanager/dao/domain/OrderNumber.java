package com.example.springmanager.dao.domain;

import java.time.LocalDate;

public class OrderNumber {
    private static Long ordersNumber = (long) 0;

    private static LocalDate lastDate = LocalDate.now();

    public OrderNumber() {
    }

    public static void incrementNumber() {
        ++ordersNumber;
    }

    public static void setOrdersNumber(Long ordersNumber) {
        OrderNumber.ordersNumber = ordersNumber;
    }

    public static Long getOrdersNumber() {
        return ordersNumber;
    }

    public static LocalDate getLastDate() {
        return lastDate;
    }

    public static void setLastDate(LocalDate lastDate) {
        OrderNumber.lastDate = lastDate;
    }
}
