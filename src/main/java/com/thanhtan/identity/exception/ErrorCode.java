package com.thanhtan.identity.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1010, "Resource not found", HttpStatus.NOT_FOUND),
    FILL_IN_THE_INPUT_FIELD(1011, "Fill in the input field", HttpStatus.BAD_REQUEST),
    ALREADY_RATED(1011, "User has already rated this product.", HttpStatus.FORBIDDEN),
    PRODUCT_QUANTITY_EXCEEDED(1012, "The requested quantity exceeds available stock.", HttpStatus.BAD_REQUEST),
    RESOURCE_EXISTED(1013, "Resource existed", HttpStatus.BAD_REQUEST),
    CLOUDINARY_DELETE_FAIL(1014, "Delete the old image fail on cloud", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1015, "Role not existed", HttpStatus.BAD_REQUEST),
    COUPON_NOT_EXISTED(1016, "Coupon code not existed", HttpStatus.BAD_REQUEST),
    NOTIFICATION_NOT_EXISTED(1016, "Notification code not existed", HttpStatus.BAD_REQUEST),
    USER_INACTIVE(1017, "User have been deleted", HttpStatus.BAD_REQUEST),
    PAYMENT_FAILED(1018, "Payment failed", HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
