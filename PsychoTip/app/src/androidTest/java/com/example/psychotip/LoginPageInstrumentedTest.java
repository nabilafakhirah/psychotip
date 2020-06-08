package com.example.psychotip;

import android.content.Intent;

import com.example.psychotip.model.LoginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class LoginPageInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginPage> loginTestRule =
            new ActivityTestRule<>(LoginPage.class);

    @Test
    public void testUsernameField() {
        loginTestRule.launchActivity(new Intent());
        onView(withId(R.id.edtUsername)).check(matches(notNullValue()));
<<<<<<< HEAD
        onView(withId(R.id.edtUsername)).check(matches(withHint("Email")));
=======
        onView(withId(R.id.edtUsername)).check(matches(withHint("Username")));
>>>>>>> cebebe16d7287aa73098880b5355f592856abb3b
    }

    @Test
    public void testPasswordField() {
        loginTestRule.launchActivity(new Intent());
        onView(withId(R.id.edtPassword)).check(matches(notNullValue()));
        onView(withId(R.id.edtPassword)).check(matches(withHint("Password")));
    }

    @Test
    public void testLoginButton() {
        loginTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnMasuk)).check(matches(notNullValue()));
        onView(withId(R.id.btnMasuk)).check(matches(withText("MASUK")));
    }

    @Test
    public void checkUsernameEditTextIsDisplayed() {
        loginTestRule.launchActivity(new Intent());
        onView(withId(R.id.edtUsername)).check(matches(isDisplayed()));
    }

    @Test
    public void checkPasswordEditTextIsDisplayed() {
        loginTestRule.launchActivity(new Intent());
        onView(withId(R.id.edtPassword)).check(matches(isDisplayed()));
    }


}
