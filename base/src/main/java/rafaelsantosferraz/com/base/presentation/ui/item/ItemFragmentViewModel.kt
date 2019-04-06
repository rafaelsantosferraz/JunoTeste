package rafaelsantosferraz.com.base.presentation.ui.item

import android.util.Log
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.interactors.item.ItemInteractor
import rafaelsantosferraz.com.base.interactors.main.MainInteractor
import rafaelsantosferraz.com.base.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class ItemFragmentViewModel @Inject constructor (
    private val itemInteractor: ItemInteractor
):  BaseViewModel<ItemFragmentViewModel.State, ItemFragmentViewModel.Command>() {

    private val TAG = "ItemFragmentViewModel"


    private var lastReadme = ""
    private var lastOwner = ""

    //region GET List --------------------------------------------------------------------------------------------------
    fun getReadme(ownerLogin: String, repoName: String) {
        Log.d(TAG, "[Item] getReadme()")
        newState(currentState().copy(isLoading = true, readme = null, isListEmpty = null))
        if (lastReadme.isBlank() || lastOwner != ownerLogin) {
            lastOwner = ownerLogin
            var readme: String? = null
            addJob(launch {
                try {
                    readme = withTimeoutOrNull(10000) {
                        itemInteractor.getReadmeAsync(ownerLogin, repoName).await()
                    }
                } catch (error: Throwable) {
                    command.postValue(Command.Error(error))
                }

                if (!readme.isNullOrBlank()) {
                    lastReadme = readme as String
                    newState(currentState().copy(isLoading = false, readme = lastReadme, isListEmpty = false))
                } else {
                    newState(currentState().copy(isLoading = false, isListEmpty = true))
                }
            })
        } else {
            newState(currentState().copy(isLoading = false, readme = lastReadme, isListEmpty = false))
        }
    }
    //endregion




    override fun initialState(): State = State()

    data class State(
        val isLoading: Boolean? = null,
        val isListEmpty: Boolean? = null,
        val readme:String? = null
    )

    sealed class Command {
        class Error(val throwable: Throwable) : Command()
    }
}