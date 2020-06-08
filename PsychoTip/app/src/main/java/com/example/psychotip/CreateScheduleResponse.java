package com.example.psychotip;

import com.google.gson.annotations.SerializedName;

public class CreateScheduleResponse {

    @SerializedName("psikolog")
    public String psikolog;

    @SerializedName("hari")
    public  String hari;

    @SerializedName("jam_kosong_start")
    public String jam_kosong_start;

    @SerializedName("jam_kosong_end")
    public String jam_kosong_end;

}
