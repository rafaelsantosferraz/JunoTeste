package rafaelsantosferraz.com.base.presentation.ui.base.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class BaseRecyclerViewItemDecorator(
    private val padding : Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        if (itemCount > 0 && itemPosition == 0) {
            top = padding
        } else if(itemCount > 0 && itemPosition > 0 && itemPosition < itemCount - 1){
            top = padding/2
            bottom = padding/2
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            bottom = padding
        }

        outRect.set(left,top,right, bottom)
    }
}