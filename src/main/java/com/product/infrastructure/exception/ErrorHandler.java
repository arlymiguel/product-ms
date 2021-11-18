package com.product.infrastructure.exception;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.product.infrastructure.exception.custom.ProductNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler{

	 @ExceptionHandler(ProductNotFoundException.class)
	    protected ResponseEntity<ErrorDetail> surpassedWeeklyMaxAmount(final ProductNotFoundException ex) {
	        log.error("surpassedWeeklyMaxAmount", ex);
	        ErrorDetail errorDetail = ErrorDetail.builder()
	                .errorCode(ErrorCodeEnum.PRODUCT_NOT_FOUND.getErrorCode())
	                .errorMessage(Optional.ofNullable(ex.getMessage()).orElse(ErrorCodeEnum.PRODUCT_NOT_FOUND.getDefaultErrorMessage()))
	                .timestamp(LocalDateTime.now(ZoneId.of("UTC")).toString())
	                .build();
	        return new ResponseEntity<>(errorDetail, ErrorCodeEnum.PRODUCT_NOT_FOUND.getHttpStatus());
	    }
	
}
