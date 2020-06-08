package com.example.psychotip;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

public class GetPsychologistSchedule {
    @SerializedName("psikolog")
    private String psikolog;

    @SerializedName("hari")
    private   String hari;

    @SerializedName("jam_kosong_start")
    private String jam_kosong_start;

    @SerializedName("jam_kosong_end")
    private String jam_kosong_end;

    public String getPsikolog() {
        return psikolog;
    }

    public String getHari() {
        return hari;
    }

    public String getJam_kosong_start() {
        return jam_kosong_start;
    }

    public String getJam_kosong_end() {
        return jam_kosong_end;
    }

    public void setPsikolog(String psikolog) {
        this.psikolog = psikolog;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setJam_kosong_start(String jam_kosong_start) {
        this.jam_kosong_start = jam_kosong_start;
    }

    public void setJam_kosong_end(String jam_kosong_end) {
        this.jam_kosong_end = jam_kosong_end;
    }
}
