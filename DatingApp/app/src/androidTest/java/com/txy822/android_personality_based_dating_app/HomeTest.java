package com.txy822.android_personality_based_dating_app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.txy822.android_personality_based_dating_app", appContext.getPackageName());
    }

    //    @Rule
//    public ActivityScenarioRule act = new ActivityScenarioRule<>(Login.class);
    @Rule
    public ActivityTestRule<Home> activityTestRule = new ActivityTestRule<>(Home.class);

    //    @Ignore
    @Test
    public void testHomeViews(){

        onView(withId(R.id.home_view_page)).check(matches(isDisplayed()));
        onView(withId(R.id.home_view_page)).check(matches(instanceOf(CustomViewPager.class)));
        onView(withId(R.id.tab)).check(matches(isDisplayed()));

    }

    @Test
    public void Finder() {

        ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 0), isDisplayed()));
        tabView.perform(click());

        onView(withId(R.id.personalityTypeAgePreferenceX)).check(matches(isDisplayed()));
        onView(withId(R.id. nameAgeLocationX)).check(matches(isDisplayed()));
        onView(withId(R.id. summaryX)).check(matches(isDisplayed()));
        onView(withId(R.id. chat_btn_id)).check(matches(isDisplayed()));
        onView(withId(R.id. compatiblity)).check(matches(isDisplayed()));
        onView(withId(R.id. distanceView)).check(matches(isDisplayed()));
        onView(withId(R.id.rejectBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.rejectBtn)).check(matches(instanceOf(ImageButton.class)));
        onView(withId(R.id.acceptBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void profile() {

        ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 1), isDisplayed()));
        tabView.perform(click());

        onView(withId(R.id. summary)).check(matches(isDisplayed()));
        onView(withId(R.id. cancel_btn_view_profile)).check(matches(isDisplayed()));
        onView(withId(R.id. fulName_id)).check(matches(isDisplayed()));
        onView(withId(R.id. location)).check(matches(isDisplayed()));
        onView(withId(R.id.personality_type)).check(matches(isDisplayed()));
        onView(withId(R.id. location)).check(matches(isDisplayed()));
        onView(withId(R.id. date_of_birth)).check(matches(isDisplayed()));
        onView(withId(R.id. age_range)).check(matches(isDisplayed()));
        onView(withId(R.id. edit)).check(matches(isDisplayed()));
        onView(withId(R.id. edit)).check(matches(isDisplayed()));

    }
    @Test
    public void Personality() {
        ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 2), isDisplayed()));
        tabView.perform(click());

//        onView(withId(R.id. fetch_some_tweets_id)).check(matches(isDisplayed()));
        onView(withId(R.id. introOfPage)).check(matches(isDisplayed()));
        onView(withId(R.id. check_personality)).check(matches(isDisplayed()));
        onView(withId(R.id. twitter_screen_name)).check(matches(isDisplayed()));
        onView(withId(R.id. view_some_tweets)).check(matches(isDisplayed()));
//        onView(withId(R.id. personality_classification_result_id)).check(matches(isDisplayed()));
        onView(withId(R.id. taketest)).check(matches(isDisplayed()));


        ViewInteraction appCompatEditText6 = onView(
                Matchers.allOf(withId(R.id.twitter_screen_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatEditText6.perform(scrollTo(), replaceText("elonmusk"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                Matchers.allOf(withId(R.id.view_some_tweets), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        materialButton5.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton6 = onView(
                Matchers.allOf(withId(R.id.check_personality), withText("Check personality"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());
    }
    @Test
    public void Setting() {
        ViewInteraction tabView = onView(Matchers.allOf(childAtPosition(childAtPosition(withId(R.id.tab), 0), 3), isDisplayed()));
        tabView.perform(click());

        onView(withId(R.id.  logout)).check(matches(isDisplayed()));
        onView(withId(R.id. logout_view_id)).check(matches(isDisplayed()));
        onView(withId(R.id. Setting_id)).check(matches(isDisplayed()));
        onView(withId(R.id. update)).check(matches(isDisplayed()));
        onView(withId(R.id. update_login)).check(matches(isDisplayed()));
        onView(withId(R.id. update_login_btn)).check(matches(isDisplayed()));
        onView(withId(R.id. delete_account)).check(matches(isDisplayed()));
        onView(withId(R.id. delete_account_btn)).check(matches(isDisplayed()));
    }


@Ignore
    @Test
    public void testMatchFinderPersonalityCheckLogout(){
        ViewInteraction tabView = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab),
                                0),
                        0),
                        isDisplayed()));
        tabView.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton9 = onView(
                Matchers.allOf(withId(R.id.acceptBtn),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatImageButton9.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction tabView4 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab),
                                0),
                        2),
                        isDisplayed()));
        tabView4.perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                Matchers.allOf(withId(R.id.twitter_screen_name),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatEditText6.perform(scrollTo(), replaceText("elonmusk"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                Matchers.allOf(withId(R.id.view_some_tweets), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        materialButton5.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton6 = onView(
                Matchers.allOf(withId(R.id.check_personality), withText("Check personality"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction tabView8 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.tab),
                                0),
                        3),
                        isDisplayed()));
        tabView8.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton10 = onView(
                Matchers.allOf(withId(R.id.logout), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        materialButton10.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    @After
    public void tearDown() throws Exception {
    }
}