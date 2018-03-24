package oriolfarrus.codewarschallenge.playerdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
class PlayerDetailFragment : Fragment(), PlayerDetailContract.PlayerDetailView {

    companion object {

        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_USERNAME = "KEY_USERNAME"

        fun newInstance(bundle: Bundle): PlayerDetailFragment {
            return PlayerDetailFragment().apply {
                this.arguments = bundle
            }
        }

        fun getBundle(player: Player): Bundle {
            return Bundle().apply {
                putString(KEY_NAME, player.name)
                putString(KEY_USERNAME, player.username)
            }
        }
    }

    @Inject lateinit var presenter: PlayerDetailPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_player_detail, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupBottomNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    private fun setupBottomNavigation() {
        //TODO setup bottom navigation
    }

    private fun setupToolbar() {
        Log.d("stuff", "title: " + getPlayerName())
        activity?.let {
            it.title = getPlayerName()
        }
    }

    private fun getPlayerName() = arguments?.getString(KEY_NAME)
}