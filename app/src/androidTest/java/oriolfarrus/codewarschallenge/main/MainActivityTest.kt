package oriolfarrus.codewarschallenge.main

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.hasDescendant
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.playerdetail.PlayerDetailFragment.Companion.KEY_USERNAME
import java.lang.Thread.sleep

/**
 * Created by oriolfarrus on 26/03/2018.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    companion object {
        const val CORRECT_USER_1 = "g964"
        const val CORRECT_USER_2 = "myjinxin2015"
        const val CORRECT_USER_3 = "Voile"
        const val INCORRECT_USER = "g964_____"
    }

    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        val countingResource = CountingIdlingResource("MainActivityIdlingResource")
        mActivityRule.activity.setIdlingResource(countingResource)
        IdlingRegistry.getInstance().register(countingResource)
    }

    @Test
    fun search_correct_user() {
        performSearch(CORRECT_USER_1)
        onView(withRecyclerView(R.id.recyclerView).atPosition(0)).check(matches(hasDescendant(withText(CORRECT_USER_1))))
    }

    @Test
    fun click_user_opens_detail() {
        performSearch(CORRECT_USER_1)

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MainFragmentAdapter.ViewHolder>(0, click() as ViewAction))
        intended(hasExtras(hasEntry(equalTo(KEY_USERNAME), equalTo(CORRECT_USER_1))))
    }

    @Test
    fun search_incorrect_user() {
        performSearch(INCORRECT_USER)
        sleep(200)
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.user_not_found))).check(matches(isDisplayed()))
    }

    @Test
    fun change_sorting() {
        performSearch(CORRECT_USER_1)
        sleep(1000)
        performSearch(CORRECT_USER_2)
        sleep(1000)
        performSearch(CORRECT_USER_3)
        sleep(1000)

        onView(withRecyclerView(R.id.recyclerView).atPosition(0)).check(matches(hasDescendant(withText(CORRECT_USER_3))))

        onView(withId(R.id.sortingSpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())

        onView(withRecyclerView(R.id.recyclerView).atPosition(0)).check(matches(hasDescendant(withText(CORRECT_USER_1))))
    }

    private fun performSearch(userName: String) {
        onView(withId(R.id.nameField)).perform(clearText(), typeText(userName))
        onView(withId(R.id.sendName)).perform(click())
    }

    // Convenience helper
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}
