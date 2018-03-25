package oriolfarrus.codewarschallenge.playerdetail

import oriolfarrus.codewarschallenge.core.model.ChallengeAuthored
import oriolfarrus.codewarschallenge.core.model.ChallengeCompleted

/**
 * Created by oriolfarrus on 25/03/2018.
 */
interface ChallengeClickListener {
    fun onChallengeClicked(challengeCompleted: ChallengeCompleted){}
    fun onChallengeClicked(challengeAuthored: ChallengeAuthored){}
}