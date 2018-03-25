package oriolfarrus.codewarschallenge.playerdetail

import oriolfarrus.codewarschallenge.core.basecontract.BasePresenter
import oriolfarrus.codewarschallenge.core.basecontract.BaseView

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface PlayerDetailContract {

    interface PlayerDetailView : BaseView {
    }

    interface PlayerDetailPresenter : BasePresenter {
        fun attachView(view: PlayerDetailContract.PlayerDetailView)
    }
}