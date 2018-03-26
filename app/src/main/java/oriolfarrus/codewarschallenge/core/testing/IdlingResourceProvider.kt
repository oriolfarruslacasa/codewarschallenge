package oriolfarrus.codewarschallenge.core.testing

import android.support.test.espresso.idling.CountingIdlingResource

/**
 * Created by oriolfarrus on 26/03/2018.
 */
interface IdlingResourceProvider {

    fun setIdlingResource(idlingResource: CountingIdlingResource)
    fun getIdlingResource(): CountingIdlingResource?
}