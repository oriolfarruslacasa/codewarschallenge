package oriolfarrus.codewarschallenge.playerdetail.completedchallenges

import android.arch.lifecycle.Lifecycle
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import oriolfarrus.codewarschallenge.core.model.ChallengeBase
import oriolfarrus.codewarschallenge.core.model.ChallengeCompletedWrapper
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.playerdetail.authoredchallenges.CompletedChallengePresenterImpl

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class CompletedChallengePresenterImplTest {

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
    }

    private val codewarsRepository: CodewarsRepository = mock()

    private val compositeDisposable: CompositeDisposable = mock()

    private val lifecycle: Lifecycle = mock()

    private val view: CompletedChallengeContract.CompletedChallengeView = mock()

    private lateinit var presenter: CompletedChallengePresenterImpl

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setup() {
        testScheduler = TestScheduler()

        presenter = CompletedChallengePresenterImpl(codewarsRepository, compositeDisposable, testScheduler, testScheduler)
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

    private fun setupEmptyApiCallResponse() {
        val wrapper = ChallengeCompletedWrapper()

        whenever(codewarsRepository.getUserCompletedChallenge(FAKE_NAME, 0)).thenReturn(Single.just(wrapper))
    }

    private fun setupApiCallResponse(): ChallengeCompletedWrapper {

        val wrapper = ChallengeCompletedWrapper().apply {
            data = listOf(ChallengeBase(), ChallengeBase())
        }

        whenever(codewarsRepository.getUserCompletedChallenge(FAKE_NAME, 0)).thenReturn(Single.just(wrapper))

        return wrapper
    }

    private fun setupViewGetNameResponse() {
        whenever(view.getPlayerName()).thenReturn(FAKE_NAME)
    }

    private fun setupLifecycleResponse() {
        whenever(view.getViewLifeCycle()).thenReturn(lifecycle)
    }
}