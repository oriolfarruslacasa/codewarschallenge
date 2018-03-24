package oriolfarrus.codewarschallenge.core.di

import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import oriolfarrus.codewarschallenge.core.database.AppDatabase
import oriolfarrus.codewarschallenge.core.network.CodewarsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by oriolfarrus on 24/03/2018.
 */

@Module
class CodewarsModule constructor(private val context: Context) {

    companion object {
        const val DATABASE_NAME = "codewars-database"
    }

    @Provides
    @Singleton
    fun getRetrofit(): CodewarsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.codewars.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create<CodewarsService>(
            CodewarsService::class.java)
    }

    @Provides
    @Singleton
    fun getDatabase(): AppDatabase {
        return Room.databaseBuilder(context,
                                    AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun getCompositeDisposable() = CompositeDisposable()

    @Provides
    @Named("io")
    fun getIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("mainThread")
    fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}