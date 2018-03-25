package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

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
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.visible
import oriolfarrus.codewarschallenge.playerdetail.authoredchallenges.CompletedChallengePresenterImpl
import oriolfarrus.codewarschallenge.playerdetail.common.ChallengeAdapter
import oriolfarrus.codewarschallenge.playerdetail.common.ChallengeClickListener
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CompletedChallengeFragment : Fragment(), CompletedChallengeContract.CompletedChallengeView, EndlessRecyclerView.EndlessRecyclerViewListener,
                                   ChallengeClickListener {

    companion object {

        private const val KEY_USERNAME = "KEY_USERNAME"

        fun newInstance(userName: String): CompletedChallengeFragment {
            return CompletedChallengeFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_USERNAME, userName)
                }
            }
        }
    }

    @Inject lateinit var presenter: CompletedChallengePresenterImpl

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

    override fun renderChallenges(dataWrapper: ChallengeCompletedWrapper) {
        val totalItems = adapter.addData(dataWrapper.data)
        endlessRecyclerview?.finishedLoading()

        if (totalItems == dataWrapper.totalItems) {
            endlessRecyclerview?.endlessRecyclerViewListener = null
            Snackbar.make(endlessRecyclerview,
                          R.string.all_challenges_loaded, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun renderError() {
        challengesError.visible()
        endlessRecyclerview.gone()
    }

    override fun getPlayerName() = arguments?.getString(KEY_USERNAME) ?: ""

    override fun onEndReached() {
        presenter.loadNextPage()
        adapter.isLoading = true
    }

    override fun onChallengeClicked(challenge: ChallengeBase) {
        context?.startActivity(ChallengeDetailActivity.getCallingIntent(context as Context, challenge))
    }

    override fun renderTimeout() {
        renderError()

        Snackbar.make(endlessRecyclerview,
                      R.string.server_timeout, Snackbar.LENGTH_INDEFINITE).apply {
            setAction(R.string.retry, ::retryAction)
            show()
        }
    }

    override fun getViewLifeCycle() = lifecycle

    private fun retryAction(view: View) {
        challengesError.gone()
        endlessRecyclerview.visible()
        presenter.retry()
    }

    private fun initRecyclerView() {
        endlessRecyclerview.adapter = adapter
        adapter.isLoading = true
        adapter.listener = this
        endlessRecyclerview.endlessRecyclerViewListener = this
    }
}