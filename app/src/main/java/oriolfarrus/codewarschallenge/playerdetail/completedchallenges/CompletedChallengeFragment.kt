package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_completed_challenge.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.gone
import oriolfarrus.codewarschallenge.core.model.ChallengeCompleted
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.visible
import oriolfarrus.codewarschallenge.playerdetail.ChallengeClickListener
import oriolfarrus.codewarschallenge.playerdetail.authoredchallenges.CompletedChallengePresenterImpl
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

    @Inject lateinit var adapter: CompletedChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_completed_challenge, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun renderChallenges(dataWrapper: ChallengeCompletedWrapper) {
        val totalItems = adapter.addData(dataWrapper.data)
        endlessRecyclerview.finishedLoading()

        if (totalItems == dataWrapper.totalItems) {
            endlessRecyclerview.endlessRecyclerViewListener = null
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

    override fun onChallengeClicked(challengeCompleted: ChallengeCompleted) {
        Toast.makeText(context, "Challenge clicked: " + challengeCompleted.name, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        endlessRecyclerview.adapter = adapter
        adapter.isLoading = true
        adapter.listener = this
        endlessRecyclerview.endlessRecyclerViewListener = this
    }
}