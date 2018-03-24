package oriolfarrus.codewarschallenge.main

import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface MainContract {

    interface MainView {
        fun renderList(user : List<Player>)
        fun renderError()
    }

    interface MainPresenter {
        fun attachView(view: MainView)
        fun detach()
    }
}