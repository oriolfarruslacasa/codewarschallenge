package oriolfarrus.codewarschallenge.core.repository

import io.reactivex.Single
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.model.Player
import oriolfarrus.codewarschallenge.core.network.CodewarsService
import javax.inject.Inject

/**
 * Created by oriolfarrus on 24/03/2018.
 */
class CodewarsRepository @Inject constructor(val codewarsService: CodewarsService) {

    fun search(name: String): Single<Player> {
        return codewarsService.getUser(name)
    }

    fun getUserCompletedChallenge(name: String, page: Int): Single<ChallengeCompletedWrapper> {
        return codewarsService.getUserCompletedChallenge(name, page)
    }

    fun getUserAuthoredChallenge(name: String): Single<ChallengeAuthoredWrapper> {
        return codewarsService.getUserAuthoredChallenge(name)
    }
}