package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface CompletedChallengeContract {

    interface CompletedChallengeView{
        fun getPlayerName() : String
        fun renderChallenges(dataWrapper: ChallengeCompletedWrapper)
        fun renderError()
    }

    interface CompletedChallengePresenter{
        fun attachView(view: CompletedChallengeView)
        fun detach()
        fun loadNextPage()
    }
}