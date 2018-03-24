package oriolfarrus.codewarschallenge

import android.app.Application
import oriolfarrus.codewarschallenge.core.di.AppComponent
import oriolfarrus.codewarschallenge.core.di.CodewarsModule
import oriolfarrus.codewarschallenge.core.di.DaggerAppComponent

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CodewarsApplication : Application() {

    companion object {

        lateinit var instance: CodewarsApplication
            private set
    }

    val daggerAppComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .codewarsModule(CodewarsModule(applicationContext))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}