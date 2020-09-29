package com.example.springmanager.service;

import com.example.springmanager.dao.domain.*;
import com.example.springmanager.dao.repository.OrderRepository;
import com.example.springmanager.exception.DepartmentsException;
import com.example.springmanager.exception.ResourceNotFoundException;
import javassist.NotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {

    private final UserService userService;
    private final OrderRepository orderRepository;

    public OrdersService(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    public Orders create(Orders orders) {

        LocalDate today = LocalDate.now();
        if (!today.equals(OrderNumber.getLastDate())) {
            synchronized (this) {
                if (!today.equals(OrderNumber.getLastDate())) {
                    OrderNumber.setLastDate(today);
                    OrderNumber.setOrdersNumber((long) 0);
                }
            }
        }

        OrderNumber.incrementNumber();
        orders.setOrderNumber(OrderNumber.getOrdersNumber());
        mostUsed(orders);

        return orderRepository.save(orders);
    }

    public Orders getMinOrders(Directions direction) throws NotFoundException {
        try {
            Orders orders = orderRepository.findByOrderNumber(orderRepository.findMinimumOrders(direction)).orElseThrow(() -> new NotFoundException("NOT FOUND"));
            return orders;
        } catch (Exception exception) {
            System.out.println("ERROR : : : " + exception.getMessage());
            throw new ResourceNotFoundException("Order");
        }
    }

    public Orders updateOrders(UserPrincipal userPrincipal, Orders input) throws NotFoundException {
        User user = userService.getByUsername(userPrincipal.getUsername());
        Orders orders = orderRepository.findByUuid(input.getUuid()).orElseThrow(() -> new NotFoundException("NOT FOUND"));
        if (orders.getStatus() == Status.WAITING) {
            orders.setStatus(Status.SERVING);
        }
        orders.setDesk(user.getDesk());
        mostUsed(orders);
        return orderRepository.save(orders);
    }

    public Orders readyOrders(Orders input) throws NotFoundException {
        Orders orders = orderRepository.findByUuid(input.getUuid()).orElseThrow(() -> new NotFoundException("NOT FOUND"));
        if (orders.getStatus() != Status.SERVING) {
            throw new ResourceNotFoundException("ILLEGAL");
        }
        orders.setStatus(Status.READY);
        mostUsed(orders);
        return orderRepository.save(orders);
    }

    public List<Orders> getAll() {
        return orderRepository.findAllOrders();
    }

    private void mostUsed(Orders orders) {
        if (orders.getStatus() == null) {
            orders.setStatus(Status.WAITING);
        } else if (orders.getStatus() == Status.SERVING) {
            orders.setServingTime(new Date());
        } else if (orders.getStatus() == Status.READY) {
            orders.setReadyTime(new Date());
        }
        if (!orders.getDepartments().getValue().equals(orders.getDirections().getValue())) {
            throw new DepartmentsException(orders.getDepartments().getValue());
        }
    }


}
