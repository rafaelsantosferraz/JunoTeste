package rafaelsantosferraz.com.base.presentation.ui.item


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.main_fragment.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.di.Injectable
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.ui.base.BaseFragment
import rafaelsantosferraz.com.base.presentation.ui.base.BaseViewModelFragment
import rafaelsantosferraz.com.base.presentation.ui.main.adapter.MainRecyclerViewAdapter
import rafaelsantosferraz.com.base.presentation.ui.main.adapter.MainRecyclerViewItemDecorator
import kotlin.reflect.KClass

class ItemFragment: BaseFragment() {


    private lateinit var item: Item

    //region BaseViewModelFragment -------------------------------------------------------------------------------------
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.main_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item = ItemFragmentArgs.fromBundle(arguments!!).item

        setupToolbar()
        setupViews()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when(item?.itemId){
            android.R.id.home -> {
                activity!!.onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
    //endregion




    //region Private ---------------------------------------------------------------------------------------------------
    private fun setupToolbar(){
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = item.owner?.login
            subtitle = item.name
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupViews(){
    }
    //endregion

}
