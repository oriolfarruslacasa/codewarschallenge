package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface AuthoredChallengeContract {

    interface AuthoredChallengeView{
        fun getPlayerName() : String
        fun renderChallenges(dataWrapper: ChallengeAuthoredWrapper)
        fun renderError()
        fun renderTimeout()
    }

    interface AuthoredChallengePresenter{
        fun attachView(view: AuthoredChallengeView)
        fun detach()
        fun retry()
    }
}