package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import oriolfarrus.codewarschallenge.core.basecontract.BasePresenter
import oriolfarrus.codewarschallenge.core.basecontract.BaseView
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface AuthoredChallengeContract {

    interface AuthoredChallengeView : BaseView {
        fun getPlayerName(): String
        fun renderChallenges(dataWrapper: ChallengeAuthoredWrapper)
        fun renderError()
        fun renderTimeout()
    }

    interface AuthoredChallengePresenter : BasePresenter {
        fun attachView(view: AuthoredChallengeView)
        fun retry()
    }
}