package oriolfarrus.codewarschallenge.challengedetail

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface ChallengeDetailContract {

    interface ChallengeDetailView {
        fun renderChallenge()
    }

    interface ChallengeDetailPresenter {
        fun attachView(view: ChallengeDetailView)
        fun detach()
    }
}