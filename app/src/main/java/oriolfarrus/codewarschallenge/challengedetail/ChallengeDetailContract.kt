package oriolfarrus.codewarschallenge.challengedetail

import oriolfarrus.codewarschallenge.core.basecontract.BasePresenter
import oriolfarrus.codewarschallenge.core.basecontract.BaseView
import oriolfarrus.codewarschallenge.core.model.ChallengeDetail

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface ChallengeDetailContract {

    interface ChallengeDetailView : BaseView {
        fun renderChallenge(challengeDetail: ChallengeDetail)
        fun getChallengeId(): String
        fun renderError()
    }

    interface ChallengeDetailPresenter : BasePresenter {
        fun attachView(view: ChallengeDetailView)
    }
}