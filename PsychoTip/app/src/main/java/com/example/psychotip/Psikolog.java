package com.example.psychotip;

import android.os.Parcel;
import android.os.Parcelable;


public class Psikolog extends User implements Parcelable {

    public static final Parcelable.Creator<Psikolog> CREATOR = new Parcelable.Creator<Psikolog>() {
        public Psikolog createFromParcel(Parcel in) {
            return new Psikolog(in);
        }

        public Psikolog[] newArray(int size) {
            return new Psikolog[size];
        }
    };

    private String namaRekening;
    private String noRekening;
    private String namaBank;
    private String nomorSipp;
    private String spesialisasi;

    public Psikolog(String username, String email, String password) {
        super(username, email, password);
        this.namaBank = "BCA";
        this.namaRekening = "Default Name";
        this.noRekening = "911111";
        this.nomorSipp = "000000";
        this.spesialisasi = "Spesial";

    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public String getNomorSipp() {
        return nomorSipp;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public void setNomorSipp(String nomorSipp) {
        this.nomorSipp = nomorSipp;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public Psikolog(Parcel in) {
        super(in);
        this.namaRekening = in.readString();
        this.noRekening = in.readString();
        this.namaBank = in.readString();
        this.nomorSipp = in.readString();
        this.spesialisasi = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getUsername());
        dest.writeString(super.getEmail());
        dest.writeString(super.getPassword());
        dest.writeString(super.getName());
        dest.writeString(super.getBirthday());
        dest.writeString(super.getGender());
        dest.writeString(super.getAddress());
        dest.writeString(this.namaRekening);
        dest.writeString(this.noRekening);
        dest.writeString(this.namaBank);
        dest.writeString(this.nomorSipp);
        dest.writeString(this.spesialisasi);
    }
}
