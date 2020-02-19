package com.bahaa.github.util

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class SpacesItemDecoration(private val space: Int, private val span: Int, private val isVertical: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space / 2
        outRect.right = space / 2
        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (span == 1) {
            if (parent.getChildLayoutPosition(view) == 0 && span == 1 && isVertical) {
                outRect.top = space
            } else {
                outRect.top = 0
            }
        }

        if (span > 1) {
            if (parent.getChildLayoutPosition(view) <= span - 1 && isVertical) {
                outRect.top = space
            } else {
                outRect.top = 0
            }
        }

    }

}