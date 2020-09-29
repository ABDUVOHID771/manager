package com.example.springmanager.dao.repository;

import com.example.springmanager.dao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
