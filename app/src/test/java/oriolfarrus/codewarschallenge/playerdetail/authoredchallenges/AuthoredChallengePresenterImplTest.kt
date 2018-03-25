package oriolfarrus.codewarschallenge.playerdetail.authoredchallenges

import android.arch.lifecycle.Lifecycle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import oriolfarrus.codewarschallenge.core.model.ChallengeAuthoredWrapper
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.playerdetail.completedchallenges.CompletedChallengePresenterImplTest

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class AuthoredChallengePresenterImplTest {

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
    }

    private val codewarsRepository: CodewarsRepository = mock()

    private val compositeDisposable: CompositeDisposable = mock()

    private val lifecycle: Lifecycle = mock()

    private val view: AuthoredChallengeContract.AuthoredChallengeView = mock()

    private lateinit var presenter: AuthoredChallengePresenterImpl

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        testScheduler = TestScheduler()

        presenter = AuthoredChallengePresenterImpl(codewarsRepository, compositeDisposable, testScheduler, testScheduler)
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
        val wrapper = setupApiCallResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(view).renderChallenges(wrapper)
    }

    @Test
    fun should_RenderError_when_wrongResponse() {
        setupLifecycleResponse()
        setupViewGetNameResponse()
        setupEmptyApiCallResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(view).renderError()
    }

    @Test
    fun should_Dispose_when_detach() {
        presenter.detach()
        verify(compositeDisposable).dispose()
    }

    private fun setupApiCallResponse(): ChallengeAuthoredWrapper {

        val wrapper = ChallengeAuthoredWrapper().apply {
            data = listOf(ChallengeBase(), ChallengeBase())
        }

        whenever(codewarsRepository.getUserAuthoredChallenge(FAKE_NAME)).thenReturn(Single.just(wrapper))

        return wrapper
    }

    private fun setupEmptyApiCallResponse() {
        val wrapper = ChallengeAuthoredWrapper()

        whenever(codewarsRepository.getUserAuthoredChallenge(FAKE_NAME)).thenReturn(Single.just(wrapper))
    }

    private fun setupViewGetNameResponse() {
        whenever(view.getPlayerName()).thenReturn(CompletedChallengePresenterImplTest.FAKE_NAME)
    }

    private fun setupLifecycleResponse() {
        whenever(view.getViewLifeCycle()).thenReturn(lifecycle)
    }
}