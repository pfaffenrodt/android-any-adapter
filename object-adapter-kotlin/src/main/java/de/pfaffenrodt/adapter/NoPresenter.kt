package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup

/**
 * Created by Dimitri Pfaffenrodt on Oktober.2017.
 */
class NoPresenter : Presenter() {

    override val layoutId: Int
        get() = 0

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): ObjectAdapter.ViewHolder {
        return ObjectAdapter.ViewHolder(itemView, this)
    }

    override fun onBindViewHolder(viewHolder: ObjectAdapter.ViewHolder, item: Any) {}
}