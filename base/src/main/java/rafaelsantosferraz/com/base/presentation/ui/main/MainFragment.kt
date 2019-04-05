package rafaelsantosferraz.com.base.presentation.ui.main


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.di.Injectable
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.ui.base.BaseViewModelFragment
import rafaelsantosferraz.com.base.presentation.ui.main.adapter.MainRecyclerViewAdapter
import rafaelsantosferraz.com.base.presentation.ui.main.adapter.MainRecyclerViewItemDecorator
import kotlin.reflect.KClass

class MainFragment: BaseViewModelFragment<MainFragmentViewModel>(), Injectable  {

    private val mainRecyclerViewAdapter: MainRecyclerViewAdapter by lazy { MainRecyclerViewAdapter() }
    private val mainRecyclerViewItemDecorator by lazy { MainRecyclerViewItemDecorator(32, 32, 16) }
    private val layoutManager by lazy { LinearLayoutManager(context) }


    override fun getViewModel(): KClass<MainFragmentViewModel> = MainFragmentViewModel::class




    //region BaseViewModelFragment -------------------------------------------------------------------------------------
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.main_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupListener()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        setupSearchView(menu)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        main_fragment_rv.removeItemDecoration(mainRecyclerViewItemDecorator)
        main_fragment_rv.layoutManager = null
        main_fragment_rv.adapter = null
    }
    //endregion




    //region Private ---------------------------------------------------------------------------------------------------
    private fun setupToolbar(){
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = resources.getString(R.string.main_title)
            subtitle = ""
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setupSearchView(menu: Menu){
        val searchView = menu.findItem(R.id.action_main_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {  viewModel.getFirstPage(query) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupRecyclerView(){
        main_fragment_rv.layoutManager = layoutManager
        main_fragment_rv.adapter = mainRecyclerViewAdapter
        main_fragment_rv.addItemDecoration(mainRecyclerViewItemDecorator)
    }

    private fun setupListener(){
        mainRecyclerViewAdapter.setOnItemClickListener { item, position, view ->
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToItemFragment(item as Item))
        }

        main_fragment_rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(1)) {
//                        if (!isLastPage && !isLoading) {
//                            viewModel.getNextPage()
//                        }
                        viewModel.getNextPage()
                    }
                }
            }
        })
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




    // region State pattern ------------------------------------------------------------------------
    private fun render(state: MainFragmentViewModel.State) {
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

            part("firstPage", state.firstPage) {
                if (it != null) {
                    handleFirstPage(it)
                }
            }

            part("nextPage", state.nextPage) {
                if (it != null) {
                    handleNextPage(it)
                }
            }

            part("savedList", state.savedList) {
                if (it != null) {
                    handleSavedList(it)
                }
            }
        }
    }


    private fun showLoadingView(isLoading: Boolean){
        Log.d(tag, "[Main] showLoadingView($isLoading)")
        if(isLoading){
            main_fragment_progressbar_pb.visibility = View.VISIBLE
        } else {
            main_fragment_progressbar_pb.visibility = View.INVISIBLE
        }
    }

    private fun handleFirstPage(list: List<Any>) {
        Log.d(tag, "[Main] handleFirstPage($list)")
        mainRecyclerViewAdapter.completeList = list
        mainRecyclerViewAdapter.submitList(list)
    }

    private fun handleNextPage(list: List<Any>) {
        Log.d(tag, "[Main] handleNextPage($list)")
        val newList = mainRecyclerViewAdapter.completeList.toMutableList()
        newList.addAll(list)
        mainRecyclerViewAdapter.completeList = newList
        mainRecyclerViewAdapter.submitList(newList)
    }

    private fun handleSavedList(list: List<Any>) {
        Log.d(tag, "[Main] handleSavedList($list)")
        mainRecyclerViewAdapter.completeList = list
        mainRecyclerViewAdapter.submitList(list)
    }

    private fun handleEmpty(isEmpty: Boolean) {
        Log.d(tag, "[Main] handleEmpty($isEmpty)")
        if (isEmpty) {
            main_fragment_empty_tv.visibility = View.VISIBLE
            mainRecyclerViewAdapter.submitList(listOf())
        } else {
            main_fragment_empty_tv.visibility = View.GONE
        }
    }
    // endregion




    // region Command pattern ----------------------------------------------------------------------
    private fun handleCommand(command: MainFragmentViewModel.Command?) {
        command.let {
            when (command) {
                is MainFragmentViewModel.Command.Error -> handleError(command.throwable)
            }
        }
    }
    // endregion

}
