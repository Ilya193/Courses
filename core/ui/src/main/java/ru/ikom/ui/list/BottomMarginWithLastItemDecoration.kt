package ru.ikom.ui.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomMarginWithLastItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = margin
    }
}