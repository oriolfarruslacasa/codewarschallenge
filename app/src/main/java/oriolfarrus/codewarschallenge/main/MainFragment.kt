package oriolfarrus.codewarschallenge.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.Player
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainFragment : Fragment(), MainContract.MainView {

    @Inject lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)

        return inflate
    }

    override fun renderList(user: List<Player>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun renderError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}