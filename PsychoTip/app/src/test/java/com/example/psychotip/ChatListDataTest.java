package com.example.psychotip;

import android.os.Parcel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ChatListDataTest {

    private ChatListData chat;

    @Before
    public void setUp() {
        chat = new ChatListData("username", "description", 1);
        chat.setUser("Sarah");
        chat.setDescription("Selamat Pagi");
        chat.setImgId(23);

    }

    @Test
    public void getUserTest() {
        assertTrue(chat.getUser().equalsIgnoreCase("Sarah"));
    }

    @Test
    public void getDescriptionTest() {
        assertTrue(chat.getDescription().equalsIgnoreCase("Selamat Pagi"));
    }

    @Test
    public void getImgIdTest() {
        assertTrue(chat.getImgId() == 23);
    }

    @Test
    public void setUserTest() {
        chat.setUser("Barry");
        assertTrue(chat.getUser().equalsIgnoreCase("Barry"));
    }

    @Test
    public void setDescriptionTest() {
        chat.setDescription("Selamat Malam");
        assertTrue(chat.getDescription().equalsIgnoreCase("Selamat Malam"));
    }

    @Test
    public void setImgIdTest() {
        chat.setImgId(99);
        assertTrue(chat.getImgId() == 99);
    }

}
