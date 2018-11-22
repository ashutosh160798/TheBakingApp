package com.example.android.thebakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTests {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void checkText_RecipeActivity() {
        Espresso.registerIdlingResources(mActivityTestRule.getActivity().idlingResource);
        onView(withId(R.id.recipe_list_rv)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void checkPlayerViewIsVisible_RecipeDetailActivity1() {
        Espresso.registerIdlingResources(mActivityTestRule.getActivity().idlingResource);
        onView(withId(R.id.recipe_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.steps_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.player_view)).check(matches(isCompletelyDisplayed()));
    }

}