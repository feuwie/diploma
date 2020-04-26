package com.simakov.diploma.Response;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class Response {
    @SuppressWarnings("rawtypes")
    private List oblist;
    private String status;
    private String message;
    private Object object;
    private String AUTH_TOKEN;
}