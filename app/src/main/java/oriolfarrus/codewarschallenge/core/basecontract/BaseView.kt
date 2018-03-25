package oriolfarrus.codewarschallenge.core.basecontract

import android.arch.lifecycle.Lifecycle

/**
 * Created by oriolfarrus on 25/03/2018.
 */
interface BaseView {
    fun getViewLifeCycle() : Lifecycle
}