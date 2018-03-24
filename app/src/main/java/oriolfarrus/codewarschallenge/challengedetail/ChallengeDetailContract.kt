package oriolfarrus.codewarschallenge.challengedetail

import oriolfarrus.codewarschallenge.core.model.Challenge

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface ChallengeDetailContract {

    interface ChallengeDetailView {
        fun renderChallenge(challenge : Challenge)
        fun renderError()
    }

    interface ChallengeDetailPresenter {
        fun attachView(view: ChallengeDetailView)
        fun detach()
    }
}