package oriolfarrus.codewarschallenge.challengedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import oriolfarrus.codewarschallenge.core.BaseActivity
import oriolfarrus.codewarschallenge.core.model.ChallengeBase

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class ChallengeDetailActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context, challenge: ChallengeBase): Intent {
            val i = Intent(context, ChallengeDetailActivity::class.java)
            val bundle = ChallengeDetailFragment.getBundle(challenge)
            i.putExtras(bundle)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ChallengeDetailFragment.newInstance(intent.extras))
            .commit()
    }
}