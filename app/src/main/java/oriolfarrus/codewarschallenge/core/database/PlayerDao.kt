package oriolfarrus.codewarschallenge.core.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Dao
interface PlayerDao {

    //TODO get last ones!
    @Query("SELECT * FROM player LIMIT 5")
    fun getAll(): Single<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: Player)
}