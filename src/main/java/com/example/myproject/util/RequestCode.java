package com.example.myproject.util;

public enum RequestCode {
        SUCCESS(200, "成功"),
        FAIL(400, "失败"),
        UNAUTHORIZED(401, "请登录");

        private int code;
        private String message;

        RequestCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }