package com.example.psychotip;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)

public class ClientCounselingCategoryInstrumentedTest {

    @Rule
    public IntentsTestRule<ClientCounselingCategory> activityRule
            = new IntentsTestRule<>(ClientCounselingCategory.class);

    @Test
    public void testClientCounselingCategoryToolbar() {
        onView(withId(R.id.my_toolbar)).check(matches(notNullValue()));
        onView(withId(R.id.toolbar_title)).check(matches(withText("Pilih Kategori")));
    }

    @Test
    public void testClientCounselingCategoryUpButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void testCounselingCategoryButton() {
        onView(withId(R.id.psychologicalProblemsId)).check(matches(notNullValue()));
    }

    @Test
    public void testCounselingCategoryIntent(){
        onView(withId(R.id.psychologicalProblemsId)).perform(click());
        intended(toPackage("com.example.psychotip"));
        intended(hasComponent(ClientChatList.class.getName()));
    }

}
