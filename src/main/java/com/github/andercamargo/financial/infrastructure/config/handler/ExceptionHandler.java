package com.github.andercamargo.financial.infrastructure.config.handler;

import com.github.andercamargo.financial.infrastructure.common.validation.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedList;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected ResponseEntity handleValidationError(
            jakarta.validation.ConstraintViolationException e) {

        var constraints = new LinkedList<String>();

        for (var constraint = e.getConstraintViolations().stream().iterator(); constraint.hasNext(); ) {
            constraints.add(constraint.next().getMessage());
        }

        return ResponseEntity.badRequest().body(constraints);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity handleError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Message: " + ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public ResponseEntity handleErrorCustom(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message: " + ex.getMessage());
    }


}
