package com.jecrc.learning_edge

import android.content.Context
import android.view.View

class ExpandableViewWrapper(private val context: Context) {
    private val expandableViewMap: MutableMap<View, View> = mutableMapOf()

    fun setupExpandableSection(button: View, expandableView: View) {
        expandableView.visibility = View.GONE
        expandableViewMap[button] = expandableView

        button.setOnClickListener {
            toggleExpandableSection(button)
        }
    }

    private fun toggleExpandableSection(button: View) {
        val expandableView = expandableViewMap[button] ?: return
        if (expandableView.visibility == View.VISIBLE) {
            expandableView.visibility = View.GONE
        } else {
            closeAllExpandableViews()
            expandableView.visibility = View.VISIBLE
        }
    }

    private fun closeAllExpandableViews() {
        for (expandableView in expandableViewMap.values) {
            expandableView.visibility = View.GONE
        }
    }
}
