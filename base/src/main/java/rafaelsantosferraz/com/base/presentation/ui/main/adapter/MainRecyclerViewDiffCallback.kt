package rafaelsantosferraz.com.base.presentation.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil

class MainRecyclerViewDiffCallback   : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = (oldItem == newItem)


    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = (oldItem == newItem)
}