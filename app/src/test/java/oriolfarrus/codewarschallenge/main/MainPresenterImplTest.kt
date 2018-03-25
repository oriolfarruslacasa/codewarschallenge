package oriolfarrus.codewarschallenge.main

import android.arch.lifecycle.Lifecycle
import com.nhaarman.mockito_kotlin.check
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import oriolfarrus.codewarschallenge.core.model.Player
import oriolfarrus.codewarschallenge.core.repository.CodewarsRepository
import oriolfarrus.codewarschallenge.core.repository.DatabaseRepository

/**
 * Created by oriolfarrus on 25/03/2018.
 */
class MainPresenterImplTest {

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
    }

    private val codewarsRepository: CodewarsRepository = mock()

    private val databaseRepository: DatabaseRepository = mock()

    private val compositeDisposable: CompositeDisposable = mock()

    private val lifecycle: Lifecycle = mock()

    private val view: MainContract.MainView = mock()

    private lateinit var presenter: MainPresenterImpl

    private lateinit var testScheduler: TestScheduler

    private lateinit var player: Player

    @Before
    fun setup() {
        testScheduler = TestScheduler()
        player = Player().apply {
            username = FAKE_NAME
            name = FAKE_NAME
        }
        presenter = MainPresenterImpl(codewarsRepository, databaseRepository, compositeDisposable, testScheduler, testScheduler)
    }

    @Test
    fun should_AttachLifecycle_when_attachView() {
        setupDatabaseResponse()
        setupLifecycleResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()

        verify(lifecycle).addObserver(presenter)
    }

    @Test
    fun should_SearchLastUsers_when_attachView() {
        setupDatabaseResponse()
        setupLifecycleResponse()

        presenter.attachView(view)
        verify(databaseRepository).getLastSearchedUsers()
    }

    @Test
    fun should_SaveToDatabase_when_search() {
        performAttach()
        setupApiCallResponse()

        executeSearch()

        verify(databaseRepository).insertPlayer(check {
            assertEquals(player, it)
            assertTrue(it.insertTime > 0)
        })
    }

    @Test
    fun should_renderPlayer_when_search() {
        performAttach()
        setupApiCallResponse()

        executeSearch()

        verify(view).renderPlayer(check {
            assertEquals(player, it)
            assertTrue(it.insertTime > 0)
        })
    }

    @Test
    fun should_Dispose_when_detach() {
        presenter.detach()
        verify(compositeDisposable).dispose()
    }

    private fun executeSearch() {
        presenter.search(FAKE_NAME)
        testScheduler.triggerActions()
    }

    private fun setupApiCallResponse() {
        whenever(codewarsRepository.search(FAKE_NAME)).thenReturn(Single.just(player))
    }

    private fun performAttach() {
        setupDatabaseResponse()
        setupLifecycleResponse()

        presenter.attachView(view)
        testScheduler.triggerActions()
    }

    private fun setupLifecycleResponse() {
        whenever(view.getViewLifeCycle()).thenReturn(lifecycle)
    }

    private fun setupDatabaseResponse() {
        whenever(databaseRepository.getLastSearchedUsers()).thenReturn(Single.just(listOf()))
    }
}