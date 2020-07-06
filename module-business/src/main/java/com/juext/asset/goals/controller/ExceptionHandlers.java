package com.juext.shop.base.controller;

import org.featx.spec.error.BaseExceptionHandler;
import org.featx.spec.error.BusinessException;
import org.featx.spec.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Excepts
 * @since 2019-10-28 14:25
 */
@ControllerAdvice
public class ExceptionHandlers extends BaseExceptionHandler {
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalStateException.class,
            HttpRequestMethodNotSupportedException.class,})
    protected <R> BaseResponse<R> handleConstraintViolationException(Exception e) {
        return super.handleConstraintViolationException(e);
    }
//
//    @Override
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
//            HttpRequestMethodNotSupportedException.class, IllegalStateException.class})
//    protected BaseResponse<Object> handleInvalidUser(BusinessException e) {
//        return super.handleConstraintViolationException(e);
//    }

    @Override
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = BusinessException.class)
    protected BaseResponse<Object> handleBusinessException(BusinessException e) {
        return super.handleConstraintViolationException(e);
    }
}
