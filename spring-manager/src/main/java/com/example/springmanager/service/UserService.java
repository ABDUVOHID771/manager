package com.example.springmanager.service;

import com.example.springmanager.dao.domain.Directions;
import com.example.springmanager.dao.domain.User;
import com.example.springmanager.dao.repository.UserRepository;
import com.example.springmanager.exception.UsernameException;
import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User update(User updating) throws NotFoundException {

        User user = userRepository.findById(updating.getId()).orElseThrow(() -> new NotFoundException("Not Found !"));

        return userRepository.save(user);

    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            if (user.getAuthorities() == null) {
                user.setAuthorities("ADMIN");
            }
            if (user.getDirection() == Directions.PHYSIC) {
                user.setDesk("A" + user.getDesk());
            } else if (user.getDirection() == Directions.LAWYER) {
                user.setDesk("B" + user.getDesk());
            } else if (user.getDirection() == Directions.IT) {
                user.setDesk("C" + user.getDesk());
            }
            return userRepository.save(user);
        } catch (Exception e) {
            System.out.println("ERROR IN CASE :::::::" + e.getMessage());
            throw new UsernameException(user.getUsername());
        }
    }

    public User getByUuid(UUID uuid) throws NotFoundException {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException("Not found"));
    }

}
