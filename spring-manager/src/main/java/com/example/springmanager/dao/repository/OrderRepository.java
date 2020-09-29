package com.example.springmanager.dao.repository;

import com.example.springmanager.dao.domain.Directions;
import com.example.springmanager.dao.domain.Orders;
import com.example.springmanager.dao.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    Optional<Orders> findByUuid(UUID uuid);

    Long deleteByUuid(UUID uuid);

    @Query(value = "SELECT min(o.orderNumber) FROM Orders o WHERE (o.status='WAITING'and o.directions=?1)")
    Long findMinimumOrders(Directions direction);

    Optional<Orders> findByOrderNumber(Long orderNumber);

    @Query(value = "SELECT o FROM Orders o WHERE (o.status='SERVING')")
    List<Orders> findAllOrders();

}
