package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

/**
 * Created by oriolfarrus on 24/03/2018.
 */
interface CompletedChallengeContract {

    interface CompletedChallengeView{

    }

    interface CompletedChallengePresenter{
        fun attachView(view: CompletedChallengeView)
        fun detach()
    }
}