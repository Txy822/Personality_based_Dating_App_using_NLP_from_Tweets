package com.txy822.android_personality_based_dating_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {

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
    public ActivityTestRule<Login> activityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void testLoginViews(){
//        ActivityTestRule<Login> activityTestRule = new ActivityTestRule<>(Login.class);
        // matches displayed test for login activity for each edit text, textview and button
        onView(withId(R.id.welcome_text)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome_text)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmail)).check(matches(instanceOf(EditText.class)));

        onView(withId(R.id.enterPassword)).check(matches(isDisplayed()));
        onView(withId(R.id.enterPassword)).check(matches(instanceOf(EditText.class)));

        onView(withId(R.id.create_account_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.forgotPassword_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.login_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.login_cancel_btn)).check(matches(isDisplayed()));
    }
    @Test
    public void testCreateAccountButton(){
        onView(withId(R.id.create_account_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.create_account_btn)).perform(click());
        onView(withId(R.id.enter_full_name)).check(matches(isDisplayed()));
        onView(withId(R.id.enter_full_name)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmail)).check(matches(instanceOf(EditText.class)));

        onView(withId(R.id.cancel_btn)).perform(click());
    }
    @Test
    public void testForgotPasswordButton(){
        onView(withId(R.id.forgotPassword_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.forgotPassword_btn)).perform(click());

        onView(withId(R.id.email_text)).check(matches(isDisplayed()));
        onView(withId(R.id.email_text)).check(matches(instanceOf(TextView.class)));

    }
    @Test
    public void testCancelButton(){
        onView(withId(R.id.login_cancel_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.login_cancel_btn)).perform(click());

        onView(withId(R.id.selector)).check(matches(isDisplayed()));
        onView(withId(R.id.selector)).check(matches(instanceOf(TextView.class)));
    }

    @After
    public void tearDown() throws Exception {
    }

}