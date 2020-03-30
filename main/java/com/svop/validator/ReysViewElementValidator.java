package com.svop.validator;

import com.svop.View.ReysViewElement;
import com.svop.service.secutity.UserService;
import com.svop.tables.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class ReysViewElementValidator implements Validator {

        @Override
        public boolean supports(Class<?> aClass) {
            //Подтвердим что aClass должен быть равер User класс
            return ReysViewElement.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {

            ReysViewElement reysViewElement=(ReysViewElement) o;
            //Утилита. Отменить
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"nomer_prilet_id",
                    "nomer.null");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "nomer_prilet_id","nomer_null");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "rout","rout_null");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "period_start","period_null");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "period_end","period_null");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "prilet_time_otpravl","prilet_time_otpravl_null");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "prilet_time_prib","prilet_time_prib_null");

            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "vilet_time_otpravl","vilet_time_otpravl_null");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,
                    "vilet_time_prib","vilet_time_prib_null");
        }
}
