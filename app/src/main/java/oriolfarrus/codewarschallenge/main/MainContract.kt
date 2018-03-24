package oriolfarrus.codewarschallenge.main

import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface MainContract {

    interface MainView {
        fun renderPlayer(player : Player)
        fun renderPlayerList(list : List<Player>)
        fun renderPlayerError()
    }

    interface MainPresenter {
        fun attachView(view: MainView)
        fun search(name : String)
        fun detach()
    }
}