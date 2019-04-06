package rafaelsantosferraz.com.base.presentation.ui.main.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MainRecyclerViewItemDecorator(
    private val startPadding : Int,
    private val endPadding : Int,
    private val middlePadding : Int

) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        // first item
        if (itemPosition == 0) {
            top = startPadding
        }
        // last item
        else if (itemCount > 0 && itemPosition == itemCount - 1) {
            bottom = endPadding
        }
        // every other item
        else {
            top = middlePadding
            bottom = middlePadding
        }

        left = middlePadding
        right = middlePadding

        outRect.set(left,top,right, bottom)
    }
}