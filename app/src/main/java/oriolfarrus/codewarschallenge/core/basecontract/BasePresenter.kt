package oriolfarrus.codewarschallenge.core.basecontract

import android.arch.lifecycle.LifecycleObserver

/**
 * Created by oriolfarrus on 25/03/2018.
 */
interface BasePresenter : LifecycleObserver {
    fun detach()
}