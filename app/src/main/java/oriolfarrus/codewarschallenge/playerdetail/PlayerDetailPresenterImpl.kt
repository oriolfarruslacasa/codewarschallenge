package oriolfarrus.codewarschallenge.playerdetail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class PlayerDetailPresenterImpl @Inject constructor() : PlayerDetailContract.PlayerDetailPresenter {

    private var view: PlayerDetailContract.PlayerDetailView? = null

    override fun attachView(view: PlayerDetailContract.PlayerDetailView) {
        this.view = view
        view.getViewLifeCycle().addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detach() {
    }
}