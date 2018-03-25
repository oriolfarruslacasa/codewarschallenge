package oriolfarrus.codewarschallenge.challengedetail

import oriolfarrus.codewarschallenge.core.model.ChallengeDetail

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface ChallengeDetailContract {

    interface ChallengeDetailView {
        fun renderChallenge(challengeDetail: ChallengeDetail)
        fun getChallengeId(): String
        fun renderError()
    }

    interface ChallengeDetailPresenter {
        fun attachView(view: ChallengeDetailView)
        fun detach()
    }
}