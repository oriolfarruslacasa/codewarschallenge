package oriolfarrus.codewarschallenge.core.repository

import io.reactivex.Single
import oriolfarrus.codewarschallenge.core.database.AppDatabase
import oriolfarrus.codewarschallenge.core.model.Player
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class DatabaseRepository @Inject constructor(private val database: AppDatabase) {

    fun getLastSearchedUsers(): Single<List<Player>> {
        return database.playerDao().getAll()
    }

    fun insertPlayer(player: Player) {
        database.playerDao().insertPlayer(player)
    }
}