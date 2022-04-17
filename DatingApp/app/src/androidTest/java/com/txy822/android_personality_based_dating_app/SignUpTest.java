package com.txy822.android_personality_based_dating_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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

public class SignUpTest {

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.txy822.android_personality_based_dating_app", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<SignUp> activityTestRule = new ActivityTestRule<>(SignUp.class);

    @Test
    public void testCreateAccountViews(){

        // matches displayed test for sign up  activity for each edit text, textview and button
        onView(withId(R.id.welcome_note)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome_note)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.enter_full_name)).check(matches(isDisplayed()));
        onView(withId(R.id.enter_full_name)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmail)).check(matches(instanceOf(EditText.class)));

        onView(withId(R.id.enter_password)).check(matches(isDisplayed()));
        onView(withId(R.id.enter_password)).check(matches(instanceOf(EditText.class)));

        onView(withId(R.id.sign_in_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_btn)).check(matches(instanceOf(Button.class)));

        onView(withId(R.id.cancel_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel_btn)).check(matches(instanceOf(Button.class)));

        onView(withId(R.id.sign_up_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up_btn)).check(matches(instanceOf(Button.class)));

    }
    @Test
    public  void testSignInButton(){
        onView(withId(R.id.sign_in_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_in_btn)).perform(click());
        onView(withId(R.id.forgotPassword_btn)).check(matches(isDisplayed()));
    }
    @Test
    public  void testCancelButton(){
        onView(withId(R.id.cancel_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel_btn)).perform(click());

        onView(withId(R.id.selector)).check(matches(isDisplayed()));
        onView(withId(R.id.selector)).check(matches(instanceOf(TextView.class)));
    }

    @After
    public void tearDown() throws Exception {
    }

}