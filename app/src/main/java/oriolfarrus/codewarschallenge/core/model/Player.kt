package oriolfarrus.codewarschallenge.core.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Entity
class Player {

    @PrimaryKey
    lateinit var username: String

    var insertTime: Long = 0L

    var leaderboardPosition: Int = 0

    lateinit var name: String

    //TODO languages!!

    override fun equals(other: Any?): Boolean {
        val otherPlayer = other as? Player
        otherPlayer?.let {
            return it.username == username
        }
        return false
    }
}