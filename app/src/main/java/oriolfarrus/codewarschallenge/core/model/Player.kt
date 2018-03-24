package oriolfarrus.codewarschallenge.core.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Entity
class Player {

    @PrimaryKey
    val username : String? = null
}