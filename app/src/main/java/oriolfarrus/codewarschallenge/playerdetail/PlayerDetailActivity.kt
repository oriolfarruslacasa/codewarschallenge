package oriolfarrus.codewarschallenge.playerdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import oriolfarrus.codewarschallenge.core.BaseActivity
import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class PlayerDetailActivity : BaseActivity() {

    companion object {
        fun getCallingIntent(context: Context, player: Player): Intent {
            val i = Intent(context, PlayerDetailActivity::class.java)
            val bundle = PlayerDetailFragment.getBundle(player)
            i.putExtras(bundle)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, PlayerDetailFragment.newInstance(intent.extras))
            .commit()
    }
}