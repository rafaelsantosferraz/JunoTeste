package rafaelsantosferraz.com.base.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import rafaelsantosferraz.com.base.App
import rafaelsantosferraz.com.base.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class,
    DataSourceModule::class,
    NetworkModule::class
//    SchedulersModule::class,
//    FirebaseModule::class,
//    SharedPreferencesModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: App)
}