package com.product.infrastructure.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {

    PRODUCT_NOT_FOUND("001","Product not found", HttpStatus.NOT_FOUND);


    private String errorCode;
    private String defaultErrorMessage;
    private HttpStatus httpStatus;
	
}
