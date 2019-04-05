package rafaelsantosferraz.com.base

import android.app.Activity
import android.app.Service
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import rafaelsantosferraz.com.base.di.AppInjector
import javax.inject.Inject

class App : MultiDexApplication(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingServiceAndroidInjector: DispatchingAndroidInjector<Service>


    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return
//        }
//        LeakCanary.install(this)


    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceAndroidInjector
    }

}