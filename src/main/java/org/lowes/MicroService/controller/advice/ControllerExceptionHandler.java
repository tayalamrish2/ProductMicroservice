package org.lowes.MicroService.controller.advice;

import org.lowes.MicroService.exception.ProductNotFoundException;
import org.lowes.MicroService.exception.ProductUpdateFailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleProductNotFoundException(ProductNotFoundException ex) {
        return "Bad request has been sent: " + ex.getMessage();
    }

    @ExceptionHandler(ProductUpdateFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleProductUpdateFailedException(ProductUpdateFailed ex) {
        return "Internal server error: " + ex.getMessage();
    }
}
