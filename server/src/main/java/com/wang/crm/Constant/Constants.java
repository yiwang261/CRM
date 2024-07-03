package com.wang.crm.Constant;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final String REDIS_CACHED_KEY = "crm:cached:";

    public static final String LOGIN_URI = "/api/login";
    public static final String REDIS_JWT_KEY = "crm:user:login";
    public static final Long EXPIRED_TIME = 7 * 24 * 60 * 60L;
    public static final Long DEFAULT_TIME = 60 * 60L;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    public static final String LOGOUT_URI = "/api/logout";
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    public static final String TOKEN_NAME = "Authorization";
    public static final String REDIS_OWNER_KEY = "crm:user:owner";
    public static final String EMPTY = "";
    public static final String EXPORT_EXCEL_URI = "/api/customer/exportExcel";
    public static final String REDIS_ONLY_NUMBER_KEY = "crm:tran:key";
}
