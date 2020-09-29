package com.example.springmanager;

import com.example.springmanager.dao.domain.*;
import com.example.springmanager.service.OrdersService;
import com.example.springmanager.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final OrdersService ordersService;
    private final UserService userService;

    public DataLoader(OrdersService ordersService, UserService userService) {
        this.ordersService = ordersService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        loader();
    }

    private void loader() {

        User user = new User();
        user.setAuthorities("ADMIN");
        user.setConfirmPassword("admin123");
        user.setPassword("admin1234");

        user.setEmail("admin@gmail.com");
        user.setUsername("admin");
        user.setDirection(Directions.IT);
        user.setDesk("11");
        userService.createUser(user);

        User user1 = new User();
        user1.setAuthorities("SUPERADMIN");
        user1.setConfirmPassword("qwer1234");
        user1.setPassword("qwer1234");
        user1.setEmail("admin@gmail.com");
        user1.setDirection(Directions.PHYSIC);
        user1.setDesk("22");
        user1.setUsername("admin1");
        userService.createUser(user1);

        User user3 = new User();
        user3.setAuthorities("VIEWER");
        user3.setConfirmPassword("viewer123");
        user3.setPassword("viewer123");

        user3.setEmail("viewer@gmail.com");
        user3.setUsername("viewer");
        user3.setDirection(Directions.IT);
        user3.setDesk("00");
        userService.createUser(user3);

        Orders orders = new Orders();
        orders.setStatus(Status.SERVING);
        orders.setDirections(Directions.IT);
        orders.setDesk("C11");
        orders.setDepartments(Departments.PROGRAMMER);
        ordersService.create(orders);
        Orders orders1 = new Orders();
        orders1.setStatus(Status.WAITING);
        orders1.setDirections(Directions.IT);
        orders1.setDepartments(Departments.PROGRAMMER);
        ordersService.create(orders1);
    }

}
