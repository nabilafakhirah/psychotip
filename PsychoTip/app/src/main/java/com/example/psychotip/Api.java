package com.example.psychotip;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface Api {
    @GET
    Call<GetUserResponse> getUser(@Url String url);

    @GET("api/retrieve_quote/")
    Call<GetQuoteOfTheDayResponse> getQotd();

    @FormUrlEncoded
    @POST("api/create_user/")
    Call<User> registerUser(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("api/login_validate/")
    Call<LoginResponse> userLogin(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("api/psychologist_schedule/")
    Call<CreateScheduleResponse> createPsychologistSchedule(@FieldMap Map<String, String> fields);

    @GET
    Call<List<GetPsychologistSchedule>> getSchedule(@Url String username);

    @FormUrlEncoded
    @POST("api/delete_schedule/")
    Call<CreateScheduleResponse> deletePsychologistSchedule(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("api/create_counseling/")
    Call<GetCreateCounselingScheduleResponse> createSchedule(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @PUT("api/update_user/")
    Call<User> updateProfile(@FieldMap Map<String, String> fields);
}
