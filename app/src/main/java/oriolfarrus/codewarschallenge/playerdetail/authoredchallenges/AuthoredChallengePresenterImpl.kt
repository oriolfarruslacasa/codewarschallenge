package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class AuthoredChallengePresenterImpl @Inject constructor(private val compositeDisposable: CompositeDisposable)
    : AuthoredChallengeContract.AuthoredChallengePresenter {

    override fun attachView(view: AuthoredChallengeContract.AuthoredChallengeView) {

    }

    override fun detach() {
        compositeDisposable.dispose()
    }
}