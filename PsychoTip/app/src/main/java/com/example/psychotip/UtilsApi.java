package com.example.psychotip;

public class UtilsApi {
    public static final String BASE_URL_API = "http://docker.ppl.cs.ui.ac.id:22503/";

    // Mendeklarasikan Interface Api
    public static Api getApiService() {
        return ApiClass.getApi(BASE_URL_API).create(Api.class);
    }

}
