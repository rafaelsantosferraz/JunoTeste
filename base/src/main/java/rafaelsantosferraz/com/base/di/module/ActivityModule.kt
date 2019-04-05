package rafaelsantosferraz.com.base.di.module

import rafaelsantosferraz.com.base.di.scope.ActivityScope
import rafaelsantosferraz.com.base.presentation.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    // Main
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}