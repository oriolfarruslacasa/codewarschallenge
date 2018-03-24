package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import io.reactivex.disposables.CompositeDisposable
import oriolfarrus.codewarschallenge.playerdetail.completedchallenges.CompletedChallengeContract
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CompletedChallengePresenterImpl @Inject constructor(private val compositeDisposable: CompositeDisposable)
    : CompletedChallengeContract.CompletedChallengePresenter {

    override fun attachView(view: CompletedChallengeContract.CompletedChallengeView) {

    }

    override fun detach() {
        compositeDisposable.dispose()
    }
}