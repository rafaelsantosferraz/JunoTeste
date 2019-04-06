package rafaelsantosferraz.com.base.presentation.ui.item


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.tiagohm.markdownview.css.styles.Github
import kotlinx.android.synthetic.main.item_fragment.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.di.Injectable
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.ui.base.BaseViewModelFragment
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import kotlin.reflect.KClass

class ItemFragment: BaseViewModelFragment<ItemFragmentViewModel>(), Injectable {


    private lateinit var item: Item

    override fun getViewModel(): KClass<ItemFragmentViewModel> = ItemFragmentViewModel::class

    //region BaseViewModelFragment -------------------------------------------------------------------------------------
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.item_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item = ItemFragmentArgs.fromBundle(arguments!!).item

        setupToolbar()
        setupObservers()
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

    override fun onResume() {
        super.onResume()
        viewModel.getReadme(item.owner?.login!!, item.name!!)
    }
    //endregion




    //region Setup ---------------------------------------------------------------------------------------------------
    private fun setupToolbar(){
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = item.owner?.login
            subtitle = item.name
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupObservers() {
        viewModel.state.observe(this, Observer {
            it?.let {
                render(it)
            }
        })

        viewModel.command.observe(this, Observer {
            it?.let {
                handleCommand(it)
            }
        })
    }
    //endregion



    //region Update
    private fun updateView(readme: String){
        item_fragment_markdown_view.addStyleSheet(Github())
        item_fragment_markdown_view.loadMarkdown(readme)
    }

    //endregion






    // region State pattern ------------------------------------------------------------------------
    private fun render(state: ItemFragmentViewModel.State) {
        Log.d(tag, "render($state)")
        viewModel.apply {
            part("isLoading", state.isLoading) {
                if (it != null) {
                    showLoadingView(it)
                }
            }

            part("isListEmpty", state.isListEmpty) {
                if (it != null) {
                    handleEmpty(it)
                }
            }

            part("readme", state.readme) {
                if (it != null) {
                    handlReadme(it)
                }
            }
        }
    }


    private fun showLoadingView(isLoading: Boolean){
        Log.d(tag, "[Item] showLoadingView($isLoading)")
        if(isLoading){
            item_fragment_progressbar_pb.visibility = View.VISIBLE
        } else {
            item_fragment_progressbar_pb.visibility = View.INVISIBLE
        }
    }

    private fun handlReadme(readme: String) {
        Log.d(tag, "[Item] handlReadme($readme)")
        updateView(readme)
    }

    private fun handleEmpty(isEmpty: Boolean) {
        Log.d(tag, "[Item] handleEmpty($isEmpty)")
        if (isEmpty) {
            item_fragment_empty_tv.visibility = View.VISIBLE
        } else {
            item_fragment_empty_tv.visibility = View.GONE
        }
    }
    // endregion




    // region Command pattern ----------------------------------------------------------------------
    private fun handleCommand(command: ItemFragmentViewModel.Command?) {
        command.let {
            when (command) {
                is ItemFragmentViewModel.Command.Error -> handleError(command.throwable)
            }
        }
    }

    override fun handleError(throwable: Throwable) {
        when(throwable) {
            is HttpException ->
                when(throwable.code()){
                    HttpURLConnection.HTTP_NOT_FOUND -> { /* No README.md file found */ }
                    else -> super.handleError(throwable)
                }
            else -> super.handleError(throwable)
        }
    }
    // endregion

}
