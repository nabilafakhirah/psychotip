package com.example.psychotip;

import android.os.Parcel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("username", "email@mail.com", "password");
        user.setName("Jane Doe");
        user.setBirthday("06/16/1999");
        user.setGender("F");
        user.setAddress("province");

        Parcel parcel = Parcel.obtain();
    }

    @Test
    public void usernameTest() {
        assertTrue(user.getUsername().equalsIgnoreCase("username"));
    }

    @Test
    public void emailTest() {
        assertTrue(user.getEmail().equalsIgnoreCase("email@mail.com"));
    }

    @Test
    public void passwordTest() {
        assertTrue(user.getPassword().equals("password"));
    }

    @Test
    public void nameTest() {
        assertTrue(user.getName().equalsIgnoreCase("Jane Doe"));
    }

    @Test
    public void birthdayTest() {
        assertTrue(user.getBirthday().equalsIgnoreCase("06/16/1999"));
    }

    @Test
    public void genderTest() {
        assertTrue(user.getGender().equalsIgnoreCase("F"));
    }

    @Test
    public void addressTest() {
        assertTrue(user.getAddress().equalsIgnoreCase("province"));
    }

    @Test
    public void setUsernameTest() {
        user.setUsername("newUsername");
        assertTrue(user.getUsername().equalsIgnoreCase("newUsername"));
    }

    @Test
    public void setEmailTest() {
        user.setEmail("newemail@gmail.com");
        assertTrue(user.getEmail().equalsIgnoreCase("newemail@gmail.com"));
    }

    @Test
    public void setPasswordTest() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void setNameTest() {
        user.setName("New Name");
        assertTrue(user.getName().equalsIgnoreCase("New Name"));
    }

    @Test
    public void setBirthdayTest() {
        user.setBirthday("16/03/1999");
        assertTrue(user.getBirthday().equalsIgnoreCase("16/03/1999"));
    }

    @Test
    public void setGenderTest() {
        user.setGender("Male");
        assertTrue(user.getGender().equalsIgnoreCase("Male"));
    }

    @Test
    public void setAddressTest() {
        user.setAddress("New Address");
        assertTrue(user.getAddress().equalsIgnoreCase("New Address"));
    }

    @Test
    public void userTest() {
        assertTrue(user instanceof User);
    }

/*    @Test
    public void userIsParcelableTest() {
        Parcel parcel = Parcel.obtain();
        user.writeToParcel(parcel, user.describeContents());
        parcel.setDataPosition(0);

        User createdFromParcel = User.CREATOR.createFromParcel(parcel);
        assertThat(createdFromParcel.getUsername(), is("newUsername"));
        assertThat(createdFromParcel.getEmail(), is("newemail@gmail.com"));
        assertThat(createdFromParcel.getPassword(), is("newpassword"));
        assertThat(createdFromParcel.getName(), is("New Name"));
        assertThat(createdFromParcel.getBirthday(), is("16/03/1999"));
        assertThat(createdFromParcel.getGender(), is("Male"));
        assertThat(createdFromParcel.getAddress(), is("New Address"));
    }*/
}

