package de.pfaffenrodt.adapter

import android.support.v4.util.SparseArrayCompat

/**
 * Presenter used with dataBinding.
 */
open class ClickableDataBindingPresenter(
        layoutId: Int,
        bindingVariableId: Int,
        private var mClickHandler: EventHandler? = null,
        bindMap: SparseArrayCompat<Any> = SparseArrayCompat()
    ): DataBindingPresenter(layoutId, bindingVariableId, bindMap) {

    override fun onBindViewHolder(viewHolder: AnyAdapter.ViewHolder, item: Any) {
        viewHolder.itemView.setOnClickListener({ mClickHandler?.onEvent(viewHolder.itemView, item)})
    }

    override fun onUnbindViewHolder(viewHolder: AnyAdapter.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        viewHolder.itemView.setOnClickListener(null)
    }
}
