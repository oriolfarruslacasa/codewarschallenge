package oriolfarrus.codewarschallenge.playerdetail

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface PlayerDetailContract {

    interface PlayerDetailView {
        fun renderPlayer()
        fun renderError()
    }

    interface PlayerDetailPresenter {
        fun attachView(view: PlayerDetailView)
        fun detach()
    }
}