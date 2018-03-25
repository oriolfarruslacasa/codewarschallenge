package oriolfarrus.codewarschallenge.challengedetail

import oriolfarrus.codewarschallenge.core.model.ChallengeCompleted

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface ChallengeDetailContract {

    interface ChallengeDetailView {
        fun renderChallenge(challenge : ChallengeCompleted)
        fun renderError()
    }

    interface ChallengeDetailPresenter {
        fun attachView(view: ChallengeDetailView)
        fun detach()
    }
}