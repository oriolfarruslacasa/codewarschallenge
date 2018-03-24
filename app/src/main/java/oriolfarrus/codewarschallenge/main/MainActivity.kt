package oriolfarrus.codewarschallenge.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import oriolfarrus.codewarschallenge.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MainFragment.newInstance())
            .commit()
    }
}
