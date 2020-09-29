package com.example.springmanager.dao.validator;

import com.example.springmanager.dao.domain.User;
import com.example.springmanager.dao.domain.UserPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserPrincipal.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "Length", "Password must be at least 8 characters");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Password must match");
        }

    }

}
