package de.pfaffenrodt.adapter

import androidx.paging.LoadState

internal class AnyLoadStateAdapter: AnyAdapter {

    /**
     * Constructs an adapter with the given [PresenterSelector].
     */
    constructor(presenterSelector: PresenterSelector) : super(presenterSelector)

    /**
     * Constructs an adapter that uses the given [Presenter] for all items.
     */
    constructor(presenter: Presenter) : super(presenter)

    /**
     * LoadState to present in the adapter.
     *
     * Changing this property will immediately notify the Adapter to change the item it's
     * presenting.
     */
    var loadState: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
        set(loadState) {
            if (field != loadState) {
                val oldItem = displayLoadStateAsItem(field)
                val newItem = displayLoadStateAsItem(loadState)

                if (oldItem && !newItem) {
                    notifyItemRemoved(0)
                } else if (newItem && !oldItem) {
                    notifyItemInserted(0)
                } else if (oldItem && newItem) {
                    notifyItemChanged(0)
                }
                field = loadState
            }
        }
    /**
     * Returns true if the LoadState should be displayed as a list item when active.
     *
     * By default, [LoadState.Loading] and [LoadState.Error] present as list items, others do not.
     */
    fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }

    override fun size(): Int {
        return if (displayLoadStateAsItem(loadState)) 1 else 0
    }

    override fun get(position: Int): Any {
        return loadState
    }
}