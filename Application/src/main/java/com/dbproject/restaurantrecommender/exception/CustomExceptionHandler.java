package com.dbproject.restaurantrecommender.exception;

import com.dbproject.restaurantrecommender.api.ResponseGenerator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    //private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public final Object handleIllegalArgumentException(IllegalArgumentException ex, final HttpServletRequest request) {
        //logger.error("[handleIllegalArgumentException] Error in Request: {} Request Params: {}", serverWebExchange.getRequest().getPath().value(), serverWebExchange.getRequest().getQueryParams(), ex);
        String errorMessage = ex.getMessage();
        return ResponseGenerator.createFailureResponse(errorMessage, HttpStatus.BAD_REQUEST, "Hi I got Bad Request");
    }


}
