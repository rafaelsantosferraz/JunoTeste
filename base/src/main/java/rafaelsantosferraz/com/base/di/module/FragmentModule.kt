package rafaelsantosferraz.com.base.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import rafaelsantosferraz.com.base.di.scope.FragmentScope
import rafaelsantosferraz.com.base.presentation.ui.item.ItemFragment
import rafaelsantosferraz.com.base.presentation.ui.main.MainFragment

@Module
abstract class FragmentModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun itemFragment(): ItemFragment

}