package rafaelsantosferraz.com.base.presentation.ui.base.adapter

import android.view.View
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer


abstract class BaseRecyclerViewAdapter(
    recyclerViewDiffCallback: DiffUtil.ItemCallback<Any> = BaseRecyclerViewDiffCallback()
): PagedListAdapter<Any, BaseRecyclerViewAdapter.BaseViewHolder>(
    recyclerViewDiffCallback
) {

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(item: Any, position: Int, view: View)
    }




    // region Public methods -----------------------------------------------------------------------
    fun setOnItemClickListener(function: (item: Any, position: Int, view: View) -> Unit) {
        onItemClickListener = object:
            OnItemClickListener {
                override fun onItemClick(item: Any, position: Int, view: View) {
                    function.invoke(item, position, view)
                }
            }
    }
    // endregion


    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) = viewHolder.bind(position)


    // region ViewHolders --------------------------------------------------------------------------
    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer{

        override val containerView: View?
            get() = view

        abstract fun bind(position: Int)
    }
    // endregion
}