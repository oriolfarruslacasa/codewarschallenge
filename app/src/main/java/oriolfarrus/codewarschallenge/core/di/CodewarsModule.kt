package oriolfarrus.codewarschallenge.core.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import oriolfarrus.codewarschallenge.core.network.CodewarsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by oriolfarrus on 24/03/2018.
 */

@Module
class CodewarsModule{

    @Provides
    fun getRetrofit(): CodewarsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.codewars.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create<CodewarsService>(
            CodewarsService::class.java)
    }
}