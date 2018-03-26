package oriolfarrus.codewarschallenge.playerdetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.junit.Rule
import org.junit.Test
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.RecyclerViewMatcher
import oriolfarrus.codewarschallenge.core.model.Player
import java.lang.Thread.sleep

/**
 * Created by oriolfarrus on 26/03/2018.
 */
class PlayerDetailActivityTest {

    companion object {
        const val CORRECT_USER_1 = "g964"
        const val INCORRECT_USER = "g964_____"
        const val CHALLENGE_NAME = "Factorial length"
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