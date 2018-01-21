package de.pfaffenrodt.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView

/**
 * Presenter used with dataBinding.
 */
open class ClickableDataBindingPresenter(
        layoutId: Int,
        bindingVariableId: Int,
        private var mClickHandler: EventHandler? = null,
        bindMap: SparseArrayCompat<Any> = SparseArrayCompat()
    ): DataBindingPresenter(layoutId, bindingVariableId, bindMap) {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
        super.onBindViewHolder(viewHolder, item)
        viewHolder.itemView.setOnClickListener({ mClickHandler?.onEvent(viewHolder.itemView, item)})
    }

    override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        viewHolder.itemView.setOnClickListener(null)
    }
}
