package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oriolfarrus.codewarschallenge.CodewarsApplication
import oriolfarrus.codewarschallenge.R
import oriolfarrus.codewarschallenge.playerdetail.authoredchallenges.CompletedChallengePresenterImpl
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CompletedChallengeFragment : Fragment(), CompletedChallengeContract.CompletedChallengeView {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodewarsApplication.instance.daggerAppComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_completed_challenge, container, false)
        presenter.attachView(this)
        return inflate
    }
}