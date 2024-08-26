package com.shadcn.identity.constant;

public class PathConstant {
    public static final String API_V1 = "/api/v1";

    public static final String PRODUCTS = "/products";
    public static final String ORDERS = "/orders";
    public static final String RATING = "/ratings";
    public static final String PERMISSION = "/permissions";
    public static final String ROLES = "/roles";
    public static final String USERS = "/users";
    public static final String AUTH = "/auth";
    public static final String CATEGORY = "/categories";
    public static final String COUPON = "/coupons";
    public static final String NOTIFICATION = "/notifications";
    public static final String VNPAYMENT = "/vnpayment";

    public static final String API_V1_PRODUCTS = API_V1 + PRODUCTS;
    public static final String API_V1_ORDERS = API_V1 + ORDERS;
    public static final String API_V1_RATING = API_V1 + RATING;
    public static final String API_V1_PERMISSION = API_V1 + PERMISSION;
    public static final String API_V1_ROLES = API_V1 + ROLES;
    public static final String API_V1_USERS = API_V1 + USERS;
    public static final String API_V1_AUTH = API_V1 + AUTH;
    public static final String API_V1_CATEGORY = API_V1 + CATEGORY;
    public static final String API_V1_COUPONS = API_V1 + COUPON;
    public static final String API_V1_NOTIFICATION = API_V1 + NOTIFICATION;
    public static final String API_V1_VNPAYMENT = API_V1 + VNPAYMENT;

    public static final String[] PUBLIC_AUTH_ENDPOINTS = {
        API_V1_AUTH + "/token",
        API_V1_AUTH + "/introspect",
        API_V1_AUTH + "/logout",
        API_V1_AUTH + "/refresh",
        API_V1_AUTH + "/outbound/authenticate",
    };

    public static final String[] PUBLIC_GET_ENDPOINTS = {
        API_V1_PRODUCTS,
        API_V1_PRODUCTS + "/category/{categoryName}",
        API_V1_PRODUCTS + "/{id}",
        API_V1_PRODUCTS + "/detail/{slug}",
        API_V1_CATEGORY,
        API_V1_CATEGORY + "/{categoryName}/products/count",
        API_V1_CATEGORY,
        API_V1_RATING,
        "/ws/**",
        "/actuator/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        API_V1_RATING + "/average/{productId}",
        API_V1_RATING + "/{id}",
        API_V1_COUPONS + "/code/{code}",
        API_V1_VNPAYMENT + "/**",
    };

    public static final String[] PUBLIC_POST_ENDPOINTS = {
        API_V1_USERS + "/registration",
    };
}
