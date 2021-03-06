package oriolfarrus.codewarschallenge.core.network

import io.reactivex.Single
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.model.ChallengeDetail
import oriolfarrus.codewarschallenge.core.model.Player
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface CodewarsService {

    @GET("users/{name}")
    fun getUser(@Path("name") name: String): Single<Player>

    @GET("users/{name}/code-challenges/completed")
    fun getUserCompletedChallenge(@Path("name") name: String,
                                  @Query("page") page: Int): Single<ChallengeCompletedWrapper>

    @GET("users/{name}/code-challenges/authored")
    fun getUserAuthoredChallenge(@Path("name") name: String): Single<ChallengeAuthoredWrapper>

    @GET("code-challenges/{id}")
    fun getChallenge(@Path("id") id: String): Single<ChallengeDetail>
}