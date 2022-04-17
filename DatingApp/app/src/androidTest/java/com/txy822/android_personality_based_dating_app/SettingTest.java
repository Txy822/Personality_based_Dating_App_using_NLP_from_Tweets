package com.txy822.android_personality_based_dating_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

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
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.google.android.material.tabs.TabLayout;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SettingTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.txy822.android_personality_based_dating_app", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<Home> activityTestRule = new ActivityTestRule<>(Home.class);
   @Test
   public void testSettingView() {
       ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 3), isDisplayed()));
       tabView.perform(click());

       onView(withId(R.id.logout)).check(matches(isDisplayed()));
       onView(withId(R.id.logout_view_id)).check(matches(isDisplayed()));
       onView(withId(R.id.Setting_id)).check(matches(isDisplayed()));
       onView(withId(R.id.update)).check(matches(isDisplayed()));
       onView(withId(R.id.update_login)).check(matches(isDisplayed()));
       onView(withId(R.id.update_login_btn)).check(matches(isDisplayed()));
       onView(withId(R.id.delete_account)).check(matches(isDisplayed()));
       onView(withId(R.id.delete_account_btn)).check(matches(isDisplayed()));

   }
       @Test
       public void testUpdateLoginButton(){

       ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 3), isDisplayed()));
       tabView.perform(click());
       onView(withId(R.id.update_login_btn)).check(matches(isDisplayed()));
       onView(withId(R.id.update_login_btn)).check(matches(instanceOf(Button.class)));

       onView(withId(R.id.update_login_btn)).perform(click());

       onView(withId(R.id.welcome_text)).check(matches(isDisplayed()));
       onView(withId(R.id.welcome_text)).check(matches(instanceOf(TextView.class)));
       onView(withId(R.id.enterEmail)).check(matches(isDisplayed()));
       onView(withId(R.id.enterEmail)).check(matches(instanceOf(EditText.class)));
       onView(withId(R.id.enterPassword)).check(matches(isDisplayed()));
       onView(withId(R.id.apply_btn)).check(matches(isDisplayed()));
       onView(withId(R.id.apply_btn)).check(matches(instanceOf(Button.class)));
       }

       @Test
       public void testUpdateButton(){
       ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 3), isDisplayed()));
       tabView.perform(click());

       onView(withId(R.id.update)).check(matches(isDisplayed()));
       onView(withId(R.id.update)).perform(click());

       onView(withId(R.id.home_view_page)).check(matches(isDisplayed()));
       onView(withId(R.id.tab)).check(matches(isDisplayed()));
       onView(withId(R.id.home_view_page)).check(matches(instanceOf(CustomViewPager.class)));


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
