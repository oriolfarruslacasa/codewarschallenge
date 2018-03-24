package oriolfarrus.codewarschallenge.main

import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.core.repository.DatabaseRepository
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainPresenterImpl @Inject constructor(private val codewarsRepository: CodewarsRepository,
                                            private val databaseRepository: DatabaseRepository,
                                            private val compositeDisposable: CompositeDisposable,
                                            @Named("io") private val ioScheduler: Scheduler,
                                            @Named("mainThread") private val mainThreadScheduler: Scheduler)
    : MainContract.MainPresenter {

    private var view: MainContract.MainView? = null

    override fun attachView(view: MainContract.MainView) {
        this.view = view
        loadLastSearchedUsers()
    }

    override fun search(name: String) {
        searchForUser(name)
    }

    override fun detach() {
        compositeDisposable.dispose()
    }

    private fun searchForUser(name: String) {
        val disposable = codewarsRepository.search(name)
            .doOnSuccess {
                it.insertTime = System.currentTimeMillis()
                databaseRepository.insertPlayer(it)
            }
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe({ player ->
                           view?.renderPlayer(player)
                       },
                       { e ->
                           Log.e("stuff", e.localizedMessage)
                           view?.renderPlayerError()
                       })

        compositeDisposable.add(disposable)
    }

    private fun loadLastSearchedUsers() {
        val disposable = databaseRepository.getLastSearchedUsers()
            .subscribeOn(ioScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe({
                           it.forEach {
                               Log.d("stuff", "Player: " + it.username + " " + it.insertTime)
                           }
                       }, { e ->
                           Log.e("stuff", "database" + e.localizedMessage)
                       })

        compositeDisposable.add(disposable)
    }
}