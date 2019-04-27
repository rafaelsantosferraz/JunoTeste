package rafaelsantosferraz.com.base.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_fragment_rv_item.*
import rafaelsantosferraz.com.base.R
import rafaelsantosferraz.com.base.domain.Item
import rafaelsantosferraz.com.base.presentation.ui.base.adapter.BaseRecyclerViewAdapter

class MainRecyclerViewAdapter : BaseRecyclerViewAdapter() {

    // region RecyclerView.Adapter methods ---------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_rv_item, parent, false))
    // endregion




    // region ViewHolders --------------------------------------------------------------------------

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