package com.example.springmanager.controller;

import com.example.springmanager.dao.domain.Directions;
import com.example.springmanager.dao.domain.User;
import com.example.springmanager.dao.validator.UserValidator;
import com.example.springmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.99.100:4200"})
@RestController
@RequestMapping("/rest/user")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (getErrors(bindingResult) != null) {
            return getErrors(bindingResult);
        }

        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> read(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok().body(userService.getByUuid(uuid));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> getErrors(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
