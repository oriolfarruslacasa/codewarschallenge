package oriolfarrus.codewarschallenge.playerdetail

import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class PlayerDetailPresenterImpl @Inject constructor() : PlayerDetailContract.PlayerDetailPresenter {

    private var view: PlayerDetailContract.PlayerDetailView? = null

    override fun attachView(view: PlayerDetailContract.PlayerDetailView) {
        this.view = view
    }

    override fun detach() {
    }
}