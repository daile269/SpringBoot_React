package com.example.springboot_reactjs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    PRODUCT_NOT_FOUND(999,"Product not found"),
    ACCOUNT_IS_EXISTS(989,"Account is exists"),
    ACCOUNT_IS_NOT_EXISTS(979,"Account is not exists"),
    USERNAME_OR_PASSWORD_VALID(969,"Username or password is valid!"),
    CATEGORY_NOT_FOUND(969,"Category not found"),
    UNAUTHENTICATED(777,"UNAUTHENTICATED" );

    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
