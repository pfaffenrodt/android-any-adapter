package de.pfaffenrodt.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * Presenter support callback of clicking item
 */
abstract class ClickablePresenter(
    private var mClickHandler: EventHandler? = null
) : Presenter() {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
        viewHolder.itemView.setOnClickListener({ mClickHandler?.onEvent(viewHolder.itemView, item)})
    }

    override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        viewHolder.itemView.setOnClickListener(null)
    }
}