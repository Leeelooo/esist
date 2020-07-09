package com.leeloo.esist.ui.recycler.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LessonItemDecorator(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params = view.layoutParams
        val position = parent.getChildAdapterPosition(view)
        outRect.set(
            margin,
            if (position == 0) margin else margin / 2,
            margin,
            margin / 2
        )
    }
}