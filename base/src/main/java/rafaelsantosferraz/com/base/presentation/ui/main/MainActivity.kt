package rafaelsantosferraz.com.base.presentation.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.presentation.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }
}
