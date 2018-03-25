package oriolfarrus.codewarschallenge.challengedetail

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class ChallengeDetailPresenterImpl @Inject constructor(private val codewarsRepository: CodewarsRepository,
                                                       private val compositeDisposable: CompositeDisposable,
                                                       @Named("io") private val ioScheduler: Scheduler,
                                                       @Named("mainThread") private val mainThreadScheduler: Scheduler)
    : ChallengeDetailContract.ChallengeDetailPresenter {

    private var view: ChallengeDetailContract.ChallengeDetailView? = null

    override fun attachView(view: ChallengeDetailContract.ChallengeDetailView) {
        this.view = view
        loadChallengeDetail(view.getChallengeId())
    }

    override fun detach() {
        compositeDisposable.dispose()
    }

    private fun loadChallengeDetail(challengeId: String) {
        val disposable = codewarsRepository.getChallenge(challengeId)
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe({view?.renderChallenge(it)},
                       {view?.renderError()})

        compositeDisposable.add(disposable)
    }
}