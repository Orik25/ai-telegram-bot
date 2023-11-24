package com.orik.adminapi.validation;

import com.orik.adminapi.DAO.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserNameUniqueValidator implements ConstraintValidator<UniqueUserName, String> {
    private UserRepository userRepository;

    public UserNameUniqueValidator() {
    }

    @Autowired
    public UserNameUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByUserName(userName).isEmpty();

    }
}