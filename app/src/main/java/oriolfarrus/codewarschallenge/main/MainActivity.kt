package oriolfarrus.codewarschallenge.main

import android.os.Bundle
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v7.app.AppCompatActivity
import oriolfarrus.codewarschallenge.core.testing.IdlingResourceProvider

class MainActivity : AppCompatActivity(), IdlingResourceProvider {

    var idling: CountingIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MainFragment.newInstance())
            .commit()
    }

    override fun setIdlingResource(idlingResource: CountingIdlingResource) {
        this.idling = idlingResource
    }

    override fun getIdlingResource(): CountingIdlingResource? = idling
}
