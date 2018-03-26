package oriolfarrus.codewarschallenge.challengedetail

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import kotlinx.android.synthetic.main.fragment_challenge_detail.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.playerdetail.PlayerDetailActivity

/**
 * Created by oriolfarrus on 26/03/2018.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ChallengeDetailActivityTest{

    companion object {
        const val CHALLENGE_NAME = "Valid Braces"
        const val CHALLENGE_ID = "5277c8a221e209d3f6000b56"
        const val CHALLENGE_INCORRECT_ID = "5277c8a221e209d3f6000b56xxxxxxx"
    }

    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<ChallengeDetailActivity> = IntentsTestRule(ChallengeDetailActivity::class.java, true, false)

    private fun setup(challengeBase: ChallengeBase) {
        val targetContext = InstrumentationRegistry.getInstrumentation()
            .targetContext
        val intent = ChallengeDetailActivity.getCallingIntent(targetContext, challengeBase)
        mActivityRule.launchActivity(intent)

        val countingResource = CountingIdlingResource("ChallengeDetailActivityIdlingResource")
        //As the Fragment is started before we can add the countingResource, we have to increment it manually
        //when debugging the countingResource is added before, in this case we have comment this line
        countingResource.increment()
        mActivityRule.activity.setIdlingResource(countingResource)
        IdlingRegistry.getInstance().register(countingResource)
    }

    @Test
    fun show_description_when_load() {
        setup(getChallenge())
        Espresso.onView(ViewMatchers.withId(R.id.challengeDetailDescription)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun message_error_when_incorrect_user() {
        setup(getIncorrectChallenge())
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.challengeError)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    private fun getChallenge() = ChallengeBase().apply {
        name = CHALLENGE_NAME
        id = CHALLENGE_ID
    }

    private fun getIncorrectChallenge() = ChallengeBase().apply {
        name = CHALLENGE_NAME
        id = CHALLENGE_INCORRECT_ID
    }
}