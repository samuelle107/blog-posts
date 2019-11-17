package com.samuelle.blogfeed.service;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
