package com.txy822.android_personality_based_dating_app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
//import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

//import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static org.hamcrest.core.IsNot.not;

import com.google.android.material.tabs.TabLayout;

import twitter4j.auth.AccessToken;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.txy822.android_personality_based_dating_app", appContext.getPackageName());
    }
    //    @Rule
//    public ActivityScenarioRule act = new ActivityScenarioRule<>(Login.class);
    @Rule
    public ActivityTestRule<Main> activityTestRule = new ActivityTestRule<>(Main.class);

    //    @Ignore
    @Test
    public void testMainViews(){
//        new ActivityTestRule<>(Main.class).getActivity();

        onView(withId(R.id.app_title)).check(matches(isDisplayed()));
        onView(withId(R.id.app_title)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.welcome_note)).check(matches(isDisplayed()));
        onView(withId(R.id.welcome_note)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.selector)).check(matches(isDisplayed()));
        onView(withId(R.id.selector)).check(matches(instanceOf(TextView.class)));

        onView(withId(R.id.to_sign_in_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.to_sign_in_btn)).check(matches(instanceOf(Button.class)));

        onView(withId(R.id.to_sign_up_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.to_sign_up_btn)).check(matches(instanceOf(Button.class)));
    }

    @Test
    public void testLoginViews(){
//        ActivityTestRule<Login> activityTestRule = new ActivityTestRule<>(Login.class);
        // matches displayed test for login activity for each edit text, textview and button
        onView(withId(R.id.to_sign_in_btn)).perform(click());
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
    public void testForgotPasswordView(){
        onView(withId(R.id.to_sign_in_btn)).perform(click());
        onView(withId(R.id.forgotPassword_btn)).perform(click());
        onView(withId(R.id.email_text)).check(matches(isDisplayed()));
        onView(withId(R.id.send_reset_link_btn)).check(matches(isDisplayed()));
        onView(withId(R.id. email_text)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
    }


    //    @Ignore
    @Test
    public void testCreateAccountViews(){
//        ActivityTestRule<SignUp> activityTestRule = new ActivityTestRule<>(SignUp.class);
        onView(withId(R.id.to_sign_up_btn)).perform(click());
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

    @NonNull
    private static ViewAction selectTabAtPosition(final int position) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TabLayout.class));
            }

            @Override
            public String getDescription() {
                return "with tab at index" + String.valueOf(position);
            }

            @Override
            public void perform(UiController uiController, View view) {
                if (view instanceof TabLayout) {
                    TabLayout tabLayout = (TabLayout) view;
                    TabLayout.Tab tab = tabLayout.getTabAt(position);

                    if (tab != null) {
                        tab.select();
                    }
                }
            }
        };
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}


