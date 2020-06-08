package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.core.IsNull.notNullValue;

public class ClientChatListInstrumentedTest {
    @Rule
    public IntentsTestRule<ClientChatList> activityRule
            = new IntentsTestRule<>(ClientChatList.class);


    @Test
    public void testClientChatListToolbar() {
        onView(withId(R.id.my_toolbar)).check(matches(notNullValue()));
        onView(withId(R.id.toolbar_title)).check(matches(withText("Percakapan")));
    }

    @Test
    public void testClientChatListToolbarUpButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void testClientChatListRecyclerView() {
        onView(withId(R.id.recyclerView)).check(matches(notNullValue()));
    }

    public void testFloatingActionButton() {
        onView(withId(R.id.new_counseling)).check(matches(notNullValue()));
    }

/*    @Test
    public void testClientCounselingCategoryIntent() {
        onView(withId(R.id.new_counseling)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ClientCounselingCategory.class.getName()));
    }*/
}
