package com.example.psychotip;

public class ChatListData {

    private String user;
    private String description;
    private int imgId;

    public ChatListData(String user, String description, int imgId) {
        this.user = user;
        this.description = description;
        this.imgId = imgId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

}
