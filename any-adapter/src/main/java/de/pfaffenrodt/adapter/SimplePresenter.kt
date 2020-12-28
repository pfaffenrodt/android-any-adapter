package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class SimplePresenter(
        @get:LayoutRes
        override val layoutId: Int
        ): Presenter() {
    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder {
        return BaseViewHolder(itemView, this)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {}
}