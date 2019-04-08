package rafaelsantosferraz.com.base.presentation.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
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
    //  https://developer.android.com/reference/android/arch/core/executor/testing/InstantTaskExecutorRule

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
        val list = listOf(Item())
        val state = MainFragmentViewModel.State(
            isLoading = false,
            isListEmpty = false,
            list = list
        )
        val mainInteractorMock = mock<MainInteractor> {
            on { runBlocking {getListAsync("a", 1)}}  doReturn GlobalScope.async(Dispatchers.Main) { list }
        }
        val mainFragmentViewModel = MainFragmentViewModel(mainInteractorMock)


        // When
        mainFragmentViewModel.getFirstPage("a")
        delay(1000)


        // Then
        mainFragmentViewModel.state.observeForever {
            assertThat(it).isEqualTo(state)
        }
    }


    @Test
    fun whenGetNextPageStateChange()  = runBlocking {

        // Given
        val list = listOf(Item())
        val state = MainFragmentViewModel.State(
            isLoading = false,
            list = list
        )
        val mainInteractorMock = mock<MainInteractor> {
            on { runBlocking {getListAsync(any(), any())}}  doReturn GlobalScope.async(Dispatchers.Main) { list }
        }
        val mainFragmentViewModel = MainFragmentViewModel(mainInteractorMock)
        mainFragmentViewModel.lastQuery = "a"
        mainFragmentViewModel.lastPage = 1


        // When
        mainFragmentViewModel.getNextPage()
        delay(1000)


        // Then
        mainFragmentViewModel.state.observeForever {
            assertThat(it).isEqualTo(state)
        }
    }


    @Test
    fun whenGetSavedListStateChange()  = runBlocking {

        // Given
        val list = listOf(Item())
        val state = MainFragmentViewModel.State(
            isLoading = false,
            isListEmpty = false,
            list = list
        )
        val mainInteractorMock = mock<MainInteractor> {
            on { runBlocking {getSavedListAsync()}}  doReturn GlobalScope.async(Dispatchers.Main) { list }
        }
        val mainFragmentViewModel = MainFragmentViewModel(mainInteractorMock)


        // When
        mainFragmentViewModel.getSavedList()
        delay(1000)


        // Then
        mainFragmentViewModel.state.observeForever {
            assertThat(it).isEqualTo(state)
        }
    }
}