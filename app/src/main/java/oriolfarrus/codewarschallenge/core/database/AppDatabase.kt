package oriolfarrus.codewarschallenge.core.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Database(entities = [(Player::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao
}