package oriolfarrus.codewarschallenge.challengedetail

import android.arch.lifecycle.Lifecycle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import oriolfarrus.codewarschallenge.core.model.ChallengeDetail
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.playerdetail.completedchallenges.CompletedChallengePresenterImplTest

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class ChallengeDetailPresenterImplTest {

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
    }

    private val codewarsRepository: CodewarsRepository = mock()

    private val compositeDisposable: CompositeDisposable = mock()

    private val lifecycle: Lifecycle = mock()

    private val view: ChallengeDetailContract.ChallengeDetailView = mock()

    private lateinit var presenter: ChallengeDetailPresenterImpl

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        testScheduler = TestScheduler()

        presenter = ChallengeDetailPresenterImpl(codewarsRepository, compositeDisposable, testScheduler, testScheduler)
    }

    @Test
    fun should_AttachLifecycle_when_attachView() {
        setupLifecycleResponse()
        setupViewGetNameResponse()
        setupApiCallResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(lifecycle).addObserver(presenter)
    }

    @Test
    fun should_RenderChallenges_when_attachView() {
        setupLifecycleResponse()
        setupViewGetNameResponse()
        val challenge = setupApiCallResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(view).renderChallenge(challenge)
    }

    @Test
    fun should_Dispose_when_detach() {
        presenter.detach()
        verify(compositeDisposable).dispose()
    }

    private fun setupApiCallResponse(): ChallengeDetail {

        val detail = ChallengeDetail()

        whenever(codewarsRepository.getChallenge(FAKE_NAME)).thenReturn(Single.just(detail))

        return detail
    }

    private fun setupViewGetNameResponse() {
        whenever(view.getChallengeId()).thenReturn(CompletedChallengePresenterImplTest.FAKE_NAME)
    }

    private fun setupLifecycleResponse() {
        whenever(view.getViewLifeCycle()).thenReturn(lifecycle)
    }
}