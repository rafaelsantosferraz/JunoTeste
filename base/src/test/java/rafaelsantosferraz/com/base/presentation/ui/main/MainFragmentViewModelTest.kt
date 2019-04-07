package rafaelsantosferraz.com.base.presentation.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.interactors.main.MainInteractor
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After


class MainFragmentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    /*  A JUnit Test Rule that swaps the background executor used by the
    //  Architecture Components with a different one which executes each task synchronously.
    //  You can use this rule for your host side tests that use Architecture Components.
    */

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    // https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun whenGetFirstPageStateChange()  = runBlocking {

        // Given
        val state = MainFragmentViewModel.State(
            isLoading = false,
            isListEmpty = false,
            list = listOf(Item())
        )
        val mainInteractorMock = mock<MainInteractor> {
            on { runBlocking {getListAsync("a", 1)}}  doReturn GlobalScope.async(Dispatchers.Main) { listOf(Item()) }
        }
        val mainFragmentViewModel = MainFragmentViewModel(mainInteractorMock)

        // When

        mainFragmentViewModel.getFirstPage("a")
        delay(1000)

        // Then
        assertThat(mainFragmentViewModel.state.value).isEqualTo(state)
    }
}