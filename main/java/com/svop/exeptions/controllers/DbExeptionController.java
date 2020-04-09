package com.svop.exeptions.controllers;

import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DbExeptionController {
private static  final Logger logger= LoggerFactory.getLogger(DbExeptionController.class);
    @ExceptionHandler(SvopDataBaseExeption.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    ExceptionResponse handleResourceNotFound(final SvopDataBaseExeption exception,
                                             final HttpServletRequest request) {
        logger.error("Ошибка базы данных...");
        logger.error(exception.getMessage());
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(exception.getMessage());
        error.callerURL(request.getRequestURI());
        error.setKod(1);
        return error;
    }

}
