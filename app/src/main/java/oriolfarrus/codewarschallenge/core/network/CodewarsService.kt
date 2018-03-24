package oriolfarrus.codewarschallenge.core.network

import io.reactivex.Single
import oriolfarrus.codewarschallenge.core.model.Player
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface CodewarsService {

    @GET("/users/{name}")
    fun listUsers(@Path("name") name: String?): Single<Player>
}