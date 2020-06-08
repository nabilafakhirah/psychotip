package com.example.psychotip;

import com.google.gson.annotations.SerializedName;

public class GetUserResponse {

    @SerializedName("username")
    public String username;

    @SerializedName("name")
    public String name;

    @SerializedName("birthday")
    public String birthday;

    @SerializedName("gender")
    public String gender;

    @SerializedName("email")
    public String email;

    @SerializedName("address")
    public String address;

    @SerializedName("role")
    public String role;

}
