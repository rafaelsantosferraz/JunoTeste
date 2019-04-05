package rafaelsantosferraz.com.base.presentation.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_fragment_rv_item.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.domain.Item

class MainRecyclerViewAdapter : ListAdapter<Any, MainRecyclerViewAdapter.BaseViewHolder>(
    MainRecyclerViewDiffCallback()
) {

    private var onItemClickListener: OnItemClickListener? = null

    var completeList: List<Any> = listOf()


    interface OnItemClickListener{
        fun onItemClick(item: Any, position: Int, view: View)
    }



    // region RecyclerView.Adapter methods ---------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_rv_item, parent, false))

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) = viewHolder.bind(position)
    // endregion



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



    // region ViewHolders --------------------------------------------------------------------------
    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

        override val containerView: View?
            get() = view

        abstract fun bind(position: Int)
    }

    // ITEM
    inner class ItemViewHolder(view: View) : BaseViewHolder(view), LayoutContainer{
        override fun bind(position: Int){
            val item = getItem(position) as Item
            view.setOnClickListener { onItemClickListener?.onItemClick(item, position, view) }

            Glide.with(view)
                .load(item.owner?.avatarUrl)
                .apply(RequestOptions()
                .circleCrop())
                .into(main_fragment_rv_item_card_avatar)
            main_fragment_rv_item_card_username.text = item.owner?.login ?: ""
            main_fragment_rv_item_card_name.text = item.name
            main_fragment_rv_item_card_description.text = item.description
        }
    }
    // endregion

}