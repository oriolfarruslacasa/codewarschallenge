package oriolfarrus.codewarschallenge.core

import android.support.test.espresso.idling.CountingIdlingResource
import android.support.v4.app.Fragment
import oriolfarrus.codewarschallenge.core.testing.IdlingResourceProvider

/**
 * Created by oriolfarrus on 26/03/2018.
 */
open class BaseFragment : Fragment(){
    protected fun getIdlingResource(): CountingIdlingResource?{
        return (activity as? IdlingResourceProvider)?.getIdlingResource()
    }
}