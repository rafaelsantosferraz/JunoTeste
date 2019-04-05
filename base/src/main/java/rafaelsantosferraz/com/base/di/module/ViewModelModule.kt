package rafaelsantosferraz.com.base.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import rafaelsantosferraz.com.base.ViewModelFactory
import rafaelsantosferraz.com.base.di.ViewModelKey
import rafaelsantosferraz.com.base.presentation.ui.main.MainFragmentViewModel
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    //Main Fragment
    @Binds
    @Singleton
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(viewModel: MainFragmentViewModel): ViewModel

}