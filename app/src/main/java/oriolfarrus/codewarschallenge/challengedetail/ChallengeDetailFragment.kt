package oriolfarrus.codewarschallenge.challengedetail

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_challenge_detail.*
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.gone
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.core.model.ChallengeDetail
import oriolfarrus.codewarschallenge.core.visible
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class ChallengeDetailFragment : Fragment(), ChallengeDetailContract.ChallengeDetailView {

    companion object {

        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_ID = "KEY_ID"

        fun newInstance(bundle: Bundle): ChallengeDetailFragment {
            return ChallengeDetailFragment().apply {
                this.arguments = bundle
            }
        }

        fun getBundle(challenge: ChallengeBase): Bundle {
            return Bundle().apply {
                putString(KEY_NAME, challenge.name)
                putString(KEY_ID, challenge.id)
            }
        }
    }

    @Inject lateinit var presenter: ChallengeDetailPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_challenge_detail, container, false)
        presenter.attachView(this)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun renderChallenge(challengeDetail: ChallengeDetail) {
        context?.let {
            showContent()
            challengeDetailDescription.text = challengeDetail.description
            challengeDetailCategory.text = it.getString(R.string.challenge_detail_category, challengeDetail.category)
        }
    }

    override fun renderError() {
        showError()
    }

    override fun getChallengeId() = arguments?.getString(KEY_ID) ?: ""

    override fun getViewLifeCycle() = lifecycle

    private fun showError() {
        progressBar.gone()
        challengeError.visible()
        challengeDetailContent.gone()
    }

    private fun showContent() {
        progressBar.gone()
        challengeDetailContent.visible()
        challengeError.gone()
    }

    private fun getChallengeName() = arguments?.getString(KEY_NAME) ?: ""

    private fun setupToolbar() {
        activity?.let {
            it.title = getChallengeName()
        }
    }
}