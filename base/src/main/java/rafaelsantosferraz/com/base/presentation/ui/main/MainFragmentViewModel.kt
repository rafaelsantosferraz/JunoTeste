package rafaelsantosferraz.com.base.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
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


    var completeList = mutableListOf<Any>()

    //region GET List --------------------------------------------------------------------------------------------------
    fun getLivePagedList(query: String) {
        Log.d(TAG, "[Main] getFirstPage()")
        newState(currentState().copy(isLoading = true, isListEmpty = null))
        var pagedList: PagedList<Any>? = null
        addJob(launch {
            try {
                pagedList = withTimeoutOrNull(10000){
                    mainInteractor.getPagedListAsync(query).await()
                }
            } catch (error: Throwable){
                command.postValue(Command.Error(error))
            }
            command.value = Command.GetPagedList(pagedList)
            newState(currentState().copy(isLoading = false, isListEmpty = false))
        })
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
        class GetPagedList(val pagedList: PagedList<Any>?) : Command()
    }
}