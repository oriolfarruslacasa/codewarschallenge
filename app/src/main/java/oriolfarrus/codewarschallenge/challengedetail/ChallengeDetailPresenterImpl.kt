package oriolfarrus.codewarschallenge.challengedetail

import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class ChallengeDetailPresenterImpl @Inject constructor() : ChallengeDetailContract.ChallengeDetailPresenter {

    private var view: ChallengeDetailContract.ChallengeDetailView? = null

    override fun attachView(view: ChallengeDetailContract.ChallengeDetailView) {
        this.view = view
    }

    override fun detach() {
    }
}