package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class AuthoredChallengePresenterImpl @Inject constructor(private val codewarsRepository: CodewarsRepository,
                                                         private val compositeDisposable: CompositeDisposable,
                                                         @Named("io") private val ioScheduler: Scheduler,
                                                         @Named("mainThread") private val mainThreadScheduler: Scheduler)
    : AuthoredChallengeContract.AuthoredChallengePresenter {

    companion object {
        const val TIMEOUT_ERROR = "timeout"
    }

    var view: AuthoredChallengeContract.AuthoredChallengeView? = null

    override fun attachView(view: AuthoredChallengeContract.AuthoredChallengeView) {
        this.view = view
        view.getViewLifeCycle().addObserver(this)
        loadAuthoredChallenges(view.getPlayerName())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detach() {
        compositeDisposable.dispose()
    }

    override fun retry() {
        view?.let {
            loadAuthoredChallenges(it.getPlayerName())
        }
    }

    private fun loadAuthoredChallenges(playerName: String) {
        val disposable = codewarsRepository.getUserAuthoredChallenge(playerName)
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(
                ::loadCompletedChallengesSuccess,
                {
                    if (it.localizedMessage == TIMEOUT_ERROR) {
                        view?.renderTimeout()
                    } else {
                        Log.e("AuthoredChallenge", it.localizedMessage)
                        view?.renderError()
                    }
                })

        compositeDisposable.add(disposable)
    }

    private fun loadCompletedChallengesSuccess(data: ChallengeAuthoredWrapper) {
        if (data.data?.isNotEmpty() == true) {
            view?.renderChallenges(data)
        } else {
            view?.renderError()
        }
    }
}