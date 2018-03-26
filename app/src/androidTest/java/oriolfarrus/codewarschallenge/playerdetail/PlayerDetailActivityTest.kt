package oriolfarrus.codewarschallenge.playerdetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.BundleMatchers
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.RecyclerViewMatcher
import oriolfarrus.codewarschallenge.challengedetail.ChallengeDetailFragment.Companion.KEY_ID
import oriolfarrus.codewarschallenge.core.model.Player
import oriolfarrus.codewarschallenge.playerdetail.common.ChallengeAdapter
import java.lang.Thread.sleep

/**
 * Created by oriolfarrus on 26/03/2018.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class PlayerDetailActivityTest {

    companion object {
        const val CORRECT_USER_1 = "g964"
        const val INCORRECT_USER = "g964_____"
        const val CHALLENGE_NAME = "Factorial length"
        const val CHALLENGE_ID = "59f34ec5a01431ab7600005a"
    }

    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<PlayerDetailActivity> = IntentsTestRule(PlayerDetailActivity::class.java, true, false)

    @Test
    fun completed_challenges_loaded() {
        setup(getCorrectPlayer())

        Espresso.onView(withRecyclerView(R.id.endlessRecyclerview).atPosition(0))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(CHALLENGE_NAME))))
    }

    @Test
    fun click_challenge_opens_detail() {
        setup(getCorrectPlayer())
        sleep(3000)
        onView(withId(R.id.endlessRecyclerview))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ChallengeAdapter.ViewHolder>(0, ViewActions.click() as ViewAction))
        Intents.intended(IntentMatchers.hasExtras(BundleMatchers.hasEntry(Matchers.equalTo(KEY_ID), Matchers.equalTo(CHALLENGE_ID))))
    }

    @Test
    fun message_error_when_incorrect_user() {
        setup(getIncorrectPlayer())
        sleep(3000)
        onView(withId(R.id.challengesError)).check(matches(isDisplayed()))
    }

    private fun setup(player: Player) {
        val targetContext = InstrumentationRegistry.getInstrumentation()
            .targetContext
        val intent = PlayerDetailActivity.getCallingIntent(targetContext, player)
        mActivityRule.launchActivity(intent)

        val countingResource = CountingIdlingResource("PlayerDetailActivityIdlingResource")
        //As the Fragment is started before we can add the countingResource, we have to increment it manually
        //when debugging the countingResource is added before, in this case we have comment this line
        countingResource.increment()
        mActivityRule.activity.setIdlingResource(countingResource)
        IdlingRegistry.getInstance().register(countingResource)
    }

    private fun getCorrectPlayer() = Player().apply {
        username = CORRECT_USER_1
        name = CORRECT_USER_1
    }

    private fun getIncorrectPlayer() = Player().apply {
        username = INCORRECT_USER
        name = INCORRECT_USER
    }

    // Convenience helper
    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }
}