package oriolfarrus.codewarschallenge.core.di

import dagger.Component
import oriolfarrus.codewarschallenge.challengedetail.ChallengeDetailFragment
import oriolfarrus.codewarschallenge.main.MainFragment
import oriolfarrus.codewarschallenge.playerdetail.PlayerDetailFragment

/**
 * Created by oriolfarrus on 24/03/2018.
 */
@Component(modules = [CodewarsModule::class])
interface AppComponent {

    fun inject(fragment: MainFragment)
    fun inject(fragment: PlayerDetailFragment)
    fun inject(fragment: ChallengeDetailFragment)
}