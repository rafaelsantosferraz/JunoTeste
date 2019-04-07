package rafaelsantosferraz.com.base.presentation.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<S, C> : ViewModel(), CoroutineScope {

    private var disposables = CompositeDisposable()
    private var jobs = mutableListOf<Job>()
    private val updated = HashMap<String, Any?>()

    val command = SingleLiveEvent<C>()
    val state = MutableLiveData<S>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        newState(initialState())
    }

    abstract fun initialState(): S


    //region Public ----------------------------------------------------------------------------------------------------
    fun <T> part(name: String, newValue: T, updater: (T) -> Unit) {
        if (!updated.containsKey(name) || updated[name] != newValue) {
            updater(newValue)
            updated[name] = newValue
        }
    }

    fun newState(state: S) {
        this.state.value = state
    }

    fun currentState(): S {
        return state.value!!
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun addJob(job: Job) {
        jobs.add(job)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
        jobs.forEach { it.cancel() }
    }
    //endregion
}

