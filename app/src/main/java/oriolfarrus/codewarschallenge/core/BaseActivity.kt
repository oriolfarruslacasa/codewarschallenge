package oriolfarrus.codewarschallenge.core

import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v7.app.AppCompatActivity
import oriolfarrus.codewarschallenge.core.testing.IdlingResourceProvider

/**
 * Created by oriolfarrus on 26/03/2018.
 */
open class BaseActivity : AppCompatActivity(), IdlingResourceProvider {

    private var idling: CountingIdlingResource? = null

    override fun setIdlingResource(idlingResource: CountingIdlingResource) {
        this.idling = idlingResource
    }

    override fun getIdlingResource(): CountingIdlingResource? = idling
}