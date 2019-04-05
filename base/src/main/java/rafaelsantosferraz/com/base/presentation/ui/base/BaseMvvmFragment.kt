package rafaelsantosferraz.com.base.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseMvvmFragment<VM : ViewModel>: BaseViewModelFragment<VM>() {

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(getViewModel().java)
    }
}