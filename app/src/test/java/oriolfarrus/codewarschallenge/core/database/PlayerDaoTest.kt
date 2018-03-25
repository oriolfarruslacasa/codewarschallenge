package oriolfarrus.codewarschallenge.core.database

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import oriolfarrus.codewarschallenge.core.model.Player

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class PlayerDaoTest {

    companion object {
        const val PLAYER_NAME = "PLAYER_NAME"
        const val PLAYER_USER_NAME = "PLAYER_USER_NAME"
    }

    @Rule
    @JvmField
    public var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: AppDatabase

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetPlayerTest() {
        val player = Player().apply {
            name = PLAYER_NAME
            username = PLAYER_USER_NAME
        }

        database.playerDao().insertPlayer(player)

        database.playerDao().getAll().test().assertValue {
            val loadedPlayer = it.getOrNull(0)

            player == loadedPlayer
        }
    }
}