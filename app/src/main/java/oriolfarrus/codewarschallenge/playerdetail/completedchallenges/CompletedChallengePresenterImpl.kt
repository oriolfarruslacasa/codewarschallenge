package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.playerdetail.completedchallenges.CompletedChallengeContract
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CompletedChallengePresenterImpl @Inject constructor(private val codewarsRepository: CodewarsRepository,
                                                          private val compositeDisposable: CompositeDisposable,
                                                          @Named("io") private val ioScheduler: Scheduler,
                                                          @Named("mainThread") private val mainThreadScheduler: Scheduler)
    : CompletedChallengeContract.CompletedChallengePresenter {

    companion object {
        const val TIMEOUT_ERROR = "timeout"
    }

    private var view: CompletedChallengeContract.CompletedChallengeView? = null
    private var page = 0

    override fun attachView(view: CompletedChallengeContract.CompletedChallengeView) {
        this.view = view
        loadCompletedChallenges(view.getPlayerName())
    }

    override fun detach() {
        compositeDisposable.dispose()
    }

    override fun loadNextPage() {
        view?.let {
            loadCompletedChallenges(it.getPlayerName())
        }
    }

    override fun retry() {
        view?.let {
            loadCompletedChallenges(it.getPlayerName())
        }
    }

    private fun loadCompletedChallenges(playerName: String) {
        val disposable = codewarsRepository.getUserCompletedChallenge(playerName, page)
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(::loadCompletedChallengesSuccess, ::loadCompletedChallengesError)

        compositeDisposable.add(disposable)
    }

    private fun loadCompletedChallengesSuccess(data: ChallengeCompletedWrapper) {
        if (data.data.isNotEmpty()) {
            page++
            view?.renderChallenges(data)
        } else if (page == 0) {
            view?.renderError()
        }
    }

    //I assume that if there's an error no more pages can be loaded, no loadFinished() is going to be called in the Fragment
    private fun loadCompletedChallengesError(throwable: Throwable) {
        if (throwable.localizedMessage == TIMEOUT_ERROR) {
            view?.renderTimeout()
        } else {
            Log.e("completedChallenge", throwable.localizedMessage + " " + throwable.message)
            if (page == 0) {
                view?.renderError()
            }
        }
    }
}