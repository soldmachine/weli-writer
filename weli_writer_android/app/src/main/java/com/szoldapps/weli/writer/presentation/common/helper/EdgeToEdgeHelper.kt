package com.szoldapps.weli.writer.presentation.common.helper

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding

fun View.setupEdgeToEdge(
    topView: View? = null,
    bottomView: View? = null,
    bottomViewMargin: View? = null,
    bottomMarginDp: Int = 16,
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        topView?.updatePadding(top = insets.top)
        bottomView?.updatePadding(bottom = insets.bottom)
        bottomViewMargin?.updateLayoutParams<MarginLayoutParams> {
            val density = v.resources.displayMetrics.density
            bottomMargin = insets.bottom + (bottomMarginDp * density).toInt()
        }
        windowInsets
    }
}
