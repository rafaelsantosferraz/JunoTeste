package  rafaelsantosferraz.com.base.presentation.ui.base

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import rafaelsantosferraz.com.base.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

//    @Inject
//    lateinit var cleanPreferencesData: CleanPreferencesData

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


    fun setMessageContent(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}