package rafaelsantosferraz.com.base.presentation.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil

class BaseRecyclerViewDiffCallback   : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = (oldItem == newItem)


    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = (oldItem == newItem)
}