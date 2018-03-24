package oriolfarrus.codewarschallenge.core.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Entity
class Player {

    @PrimaryKey
    lateinit var username : String

    var insertTime : Long = 0L
}