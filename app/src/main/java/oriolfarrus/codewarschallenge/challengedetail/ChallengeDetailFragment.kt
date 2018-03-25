package oriolfarrus.codewarschallenge.challengedetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class ChallengeDetailFragment : Fragment(), ChallengeDetailContract.ChallengeDetailView {

    companion object {

        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_USERNAME = "KEY_USERNAME"

        fun newInstance(bundle: Bundle): ChallengeDetailFragment {
            return ChallengeDetailFragment().apply {
                this.arguments = bundle
            }
        }

        fun getBundle(challenge: ChallengeBase): Bundle {
            return Bundle().apply {

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
        renderChallenge()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detach()
    }

    override fun renderChallenge() {
    }
}