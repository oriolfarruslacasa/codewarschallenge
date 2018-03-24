package oriolfarrus.codewarschallenge

import android.app.Application
import android.arch.persistence.room.Room
import oriolfarrus.codewarschallenge.core.database.AppDatabase
import oriolfarrus.codewarschallenge.core.di.AppComponent
import oriolfarrus.codewarschallenge.core.di.DaggerAppComponent

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CodewarsApplication : Application() {

    companion object {

        lateinit var instance: CodewarsApplication
            private set

        const val DATABASE_NAME = "codewars-database"
    }

    val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext,
                             AppDatabase::class.java, DATABASE_NAME).build()
    }

    val daggerAppComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}