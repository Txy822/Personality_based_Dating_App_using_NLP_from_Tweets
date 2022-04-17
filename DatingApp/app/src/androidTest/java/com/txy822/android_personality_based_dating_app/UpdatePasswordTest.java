package com.txy822.android_personality_based_dating_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UpdatePasswordTest {

    @Before
    public void setUp() throws Exception {
    }
    @Rule
    public ActivityTestRule<UpdatePassword> activityTestRule = new ActivityTestRule<>(UpdatePassword.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.txy822.android_personality_based_dating_app", appContext.getPackageName());
    }
    @Test
    public void testUpdatePasswordViews(){

        // matches displayed test for sign up  activity for each edit text, textview and button
        onView(withId(R.id.send_reset_link_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.send_reset_link_btn)).check(matches(instanceOf(Button.class)));

        onView(withId(R.id.email_text)).check(matches(isDisplayed()));
        onView(withId(R.id.email_text)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmail)).check(matches(instanceOf(EditText.class)));


    }
}