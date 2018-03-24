package oriolfarrus.codewarschallenge.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.Player
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class MainFragment : Fragment(), MainContract.MainView {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_main, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonBehaviour()
    }

    override fun renderPlayer(player: Player) {
        Toast.makeText(context, player.username, Toast.LENGTH_SHORT).show()
    }

    override fun renderPlayerError() {
        //TODO renderPlayerError
        Toast.makeText(context, "Error loading user", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    private fun setButtonBehaviour() {
        sendName.setOnClickListener {
            val name = nameField.text.toString()

            if (name.isNotEmpty()) {
                presenter.search(name)
            }

        }
    }
}