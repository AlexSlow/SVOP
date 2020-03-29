package com.svop.validator;

import com.svop.service.secutity.UserService;
import com.svop.tables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator  implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        //Подтвердим что aClass должен быть равер User класс
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user=(User)o;
        //Утилита. Отменить
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","username_empty_error");
        if (user.getUsername().length()<8||user.getUsername().length()>32)
        {
            errors.rejectValue("username","username_size_error");
        }
        if(userService.findByUsername(user.getUsername())!=null)
        {
            errors.rejectValue("username","username_unique_error");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","password_empty_error");
        if (user.getPassword().length()<8||user.getPassword().length()>32)
        {
            errors.rejectValue("password","password_size_error");
        }
        if(!user.getConfirmPassword().equals(user.getPassword()))
        {
            errors.rejectValue("confirmPassword","password_confirm_error");
        }
    }
}
