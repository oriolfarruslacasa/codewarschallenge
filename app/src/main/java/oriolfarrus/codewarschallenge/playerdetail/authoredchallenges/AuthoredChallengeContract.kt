package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface AuthoredChallengeContract {

    interface AuthoredChallengeView{

    }

    interface AuthoredChallengePresenter{
        fun attachView(view: AuthoredChallengeView)
        fun detach()
    }
}