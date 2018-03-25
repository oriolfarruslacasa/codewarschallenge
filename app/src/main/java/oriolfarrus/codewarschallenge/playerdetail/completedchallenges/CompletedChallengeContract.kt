package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import oriolfarrus.codewarschallenge.core.basecontract.BasePresenter
import oriolfarrus.codewarschallenge.core.basecontract.BaseView
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface CompletedChallengeContract {

    interface CompletedChallengeView : BaseView {
        fun getPlayerName(): String
        fun renderChallenges(dataWrapper: ChallengeCompletedWrapper)
        fun renderError()
        fun renderTimeout()
    }

    interface CompletedChallengePresenter : BasePresenter {
        fun attachView(view: CompletedChallengeView)
        fun loadNextPage()
        fun retry()
    }
}