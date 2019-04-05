package rafaelsantosferraz.com.base.presentation.ui.main

import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.interactors.main.MainInteractor
import rafaelsantosferraz.com.base.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor (
    private val mainInteractor: MainInteractor
):  BaseViewModel<MainFragmentViewModel.State, MainFragmentViewModel.Command>() {

    private val TAG = "MainFragmentViewModel"

    private var lastQuery = ""
    private var lastPage = 1
    private var lastList = mutableListOf<Any>()

    //region GET List --------------------------------------------------------------------------------------------------
    fun getFirstPage(query: String) {
        Log.d(TAG, "[Main] getFirstPage()")
        newState(currentState().copy(isLoading = true, firstPage = null, nextPage = null, isListEmpty = null))
        var list: List<Any>? = null
        addJob(launch {
            try {
                list = withTimeoutOrNull(10000){
                    mainInteractor.getListAsync(query, 1).await()
                }
            } catch (error: Throwable){
                command.postValue(Command.Error(error))
            }

            if (!list.isNullOrEmpty()) {
                lastList = (list as List<Any>).toMutableList()
                newState(currentState().copy(isLoading = false, firstPage = list, isListEmpty = false))
            } else {
                newState(currentState().copy(isLoading = false, isListEmpty = true))
            }
        })

        lastQuery = query
        lastPage = 1

    }
    //endregion


    //region GET List --------------------------------------------------------------------------------------------------
    fun getNextPage() {
        Log.d(TAG, "[Main] getNextPage()")
        newState(currentState().copy(isLoading = true, nextPage = null, isListEmpty = null))
        var list: List<Any>? = null
        addJob(launch {
            try {
                list = withTimeoutOrNull(10000){
                    mainInteractor.getListAsync(lastQuery, lastPage + 1).await()
                }
            } catch (error: Throwable){
                command.postValue(Command.Error(error))
            }

            if (!list.isNullOrEmpty()) {
                lastList.addAll((list as List<Any>))
                newState(currentState().copy(isLoading = false, nextPage = list, isListEmpty = false))
            } else {
                newState(currentState().copy(isLoading = false, isListEmpty = true))
            }
        })
        lastPage ++

    }
    //endregion

    //region GET List --------------------------------------------------------------------------------------------------
    fun getSavedList() {
        Log.d(TAG, "[Main] getSavedList()")
        newState(currentState().copy(savedList = null))
        newState(currentState().copy(savedList = lastList))
    }
    //endregion







    override fun initialState(): State = State()

    data class State(
        val isLoading: Boolean? = null,
        val isListEmpty: Boolean? = null,
        val savedList: List<Any>? = null,
        val firstPage: List<Any>? = null,
        val nextPage: List<Any>? = null
    )

    sealed class Command {
        class Error(val throwable: Throwable) : Command()
    }
}