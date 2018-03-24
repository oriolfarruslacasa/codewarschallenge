package oriolfarrus.codewarschallenge.main

import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainPresenterImpl @Inject constructor(val codewarsRepository: CodewarsRepository)
    : MainContract.MainPresenter {

    override fun attachView(view: MainContract.MainView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}