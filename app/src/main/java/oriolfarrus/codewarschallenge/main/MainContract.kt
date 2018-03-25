package oriolfarrus.codewarschallenge.main

import oriolfarrus.codewarschallenge.core.basecontract.BasePresenter
import oriolfarrus.codewarschallenge.core.basecontract.BaseView
import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface MainContract {

    interface MainView : BaseView {
        fun renderPlayer(player: Player)
        fun renderPlayerList(list: List<Player>)
        fun renderPlayerError()
    }

    interface MainPresenter : BasePresenter {
        fun attachView(view: MainView)
        fun search(name: String)
    }
}