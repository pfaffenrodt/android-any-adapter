package de.pfaffenrodt.adapter.sample

import de.pfaffenrodt.adapter.DiffCallback

class SampleDiffCallback : DiffCallback<BaseItem<*>>() {
    override fun areItemsTheSame(oldItem: BaseItem<*>, newItem: BaseItem<*>): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem<*>, newItem: BaseItem<*>): Boolean {
        return oldItem.value == newItem.value
    }
}