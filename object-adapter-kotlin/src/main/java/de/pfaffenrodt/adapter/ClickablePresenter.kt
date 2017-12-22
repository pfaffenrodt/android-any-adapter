package de.pfaffenrodt.adapter

/**
 * Presenter support callback of clicking item
 */
abstract class ClickablePresenter(
    private var mClickHandler: EventHandler? = null
) : Presenter() {

    override fun onBindViewHolder(viewHolder: AnyAdapter.ViewHolder, item: Any) {
        viewHolder.itemView.setOnClickListener({ mClickHandler?.onEvent(viewHolder.itemView, item)})
    }

    override fun onUnbindViewHolder(viewHolder: AnyAdapter.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        viewHolder.itemView.setOnClickListener(null)
    }
}