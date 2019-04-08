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

    var lastQuery = ""
    var lastPage = 1
    var isLastPage = false
    var completeList = mutableListOf<Any>()



    //region GET List --------------------------------------------------------------------------------------------------
    fun getFirstPage(query: String) {
        Log.d(TAG, "[Main] getFirstPage()")
        newState(currentState().copy(isLoading = true, list = null, isListEmpty = null))
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
                completeList = (list as List<Any>).toMutableList()
                newState(currentState().copy(isLoading = false, list = list, isListEmpty = false))
            } else {
                newState(currentState().copy(isLoading = false, isListEmpty = true))
            }
        })

        lastQuery = query
        lastPage = 1
        isLastPage = false

    }
    //endregion



    //region GET List --------------------------------------------------------------------------------------------------
    fun getNextPage() {
        if (!isLastPage && lastQuery.isNotBlank()) {
            Log.d(TAG, "[Main] getNextPage()")
            newState(currentState().copy(isLoading = true, list = null))
            var list: List<Any>? = null
            addJob(launch {
                try {
                    list = withTimeoutOrNull(10000) {
                        mainInteractor.getListAsync(lastQuery, lastPage + 1).await()
                    }
                } catch (error: Throwable) {
                    command.postValue(Command.Error(error))
                }

                if (!list.isNullOrEmpty()) {
                    completeList.addAll((list as List<Any>))
                    newState(currentState().copy(isLoading = false, list = completeList))
                } else {
                    isLastPage = true
                    newState(currentState().copy(isLoading = false))
                }
            })
            lastPage++
        }
    }
    //endregion



    //region GET List --------------------------------------------------------------------------------------------------
    fun getSavedList() {
        Log.d(TAG, "[Main] getSavedList()")
        newState(currentState().copy(isLoading = true, list = null))
        if (completeList.isEmpty()){
            var list: List<Any>? = null
            addJob(launch {
                try {
                    list = withTimeoutOrNull(10000){
                        mainInteractor.getSavedListAsync().await()
                    }
                } catch (error: Throwable){
                    command.postValue(Command.Error(error))
                }

                if (!list.isNullOrEmpty()) {
                    completeList = (list as List<Any>).toMutableList()
                    newState(currentState().copy(isLoading = false, list = completeList, isListEmpty = false))
                } else {
                    newState(currentState().copy(isLoading = false, isListEmpty = true))
                }
            })
        } else {
            newState(currentState().copy(isLoading = false, list = completeList, isListEmpty = false))
        }

    }
    //endregion


    //region GET List --------------------------------------------------------------------------------------------------
    fun saveList() {
        Log.d(TAG, "[Main] saveList()")
        var isSuccess: Boolean? = false
        addJob(launch {
            try {
                isSuccess = withTimeoutOrNull(10000){
                    mainInteractor.saveListAsync(completeList as List<Item>).await()
                }
            } catch (error: Throwable){
                command.postValue(Command.Error(error))
            }
        })
    }
    //endregion






    override fun initialState(): State = State()

    data class State(
        val isLoading: Boolean? = null,
        val isListEmpty: Boolean? = null,
        val list: List<Any>? = null
    )

    sealed class Command {
        class Error(val throwable: Throwable) : Command()
    }
}