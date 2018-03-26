package oriolfarrus.codewarschallenge.main

import android.os.Bundle
import oriolfarrus.codewarschallenge.core.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MainFragment.newInstance())
            .commit()
    }
}
