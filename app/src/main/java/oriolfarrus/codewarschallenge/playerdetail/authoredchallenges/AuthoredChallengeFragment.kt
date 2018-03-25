package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_challenges_list.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.challengedetail.ChallengeDetailActivity
import oriolfarrus.codewarschallenge.core.gone
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.core.visible
import oriolfarrus.codewarschallenge.playerdetail.common.ChallengeAdapter
import oriolfarrus.codewarschallenge.playerdetail.common.ChallengeClickListener
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class AuthoredChallengeFragment : Fragment(), AuthoredChallengeContract.AuthoredChallengeView,
                                  ChallengeClickListener {


    companion object {
        private const val KEY_USERNAME = "KEY_USERNAME"

        fun newInstance(userName: String): AuthoredChallengeFragment {
            return AuthoredChallengeFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_USERNAME, userName)
                }
            }
        }
    }

    @Inject lateinit var presenter: AuthoredChallengePresenterImpl

    @Inject lateinit var adapter: ChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_challenges_list, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun renderChallenges(dataWrapper: ChallengeAuthoredWrapper) {
        adapter.addData(dataWrapper.data)
    }

    override fun renderError() {
        challengesError.visible()
        endlessRecyclerview.gone()
    }

    override fun renderTimeout() {
        renderError()

        Snackbar.make(endlessRecyclerview,
                      R.string.server_timeout, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.retry, ::retryAction)
            show()
        }
    }

    override fun onChallengeClicked(challenge: ChallengeBase) {
        context?.startActivity(ChallengeDetailActivity.getCallingIntent(context as Context, challenge))
    }

    override fun getPlayerName() = arguments?.getString(KEY_USERNAME) ?: ""

    override fun getViewLifeCycle() = lifecycle

    private fun retryAction(view: View) {
        showRecyclerView()
        presenter.retry()
    }

    private fun showRecyclerView() {
        challengesError.gone()
        endlessRecyclerview.visible()
    }

    private fun initRecyclerView() {
        endlessRecyclerview.adapter = adapter
        adapter.isLoading = true
        adapter.listener = this
    }
}