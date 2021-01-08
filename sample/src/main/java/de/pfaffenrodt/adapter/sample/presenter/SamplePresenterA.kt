package de.pfaffenrodt.adapter.sample.presenter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.pfaffenrodt.adapter.Presenter
import de.pfaffenrodt.adapter.sample.BaseItem
import de.pfaffenrodt.adapter.sample.R

open class SamplePresenterA : Presenter() {
    override val layoutId: Int
        get() = R.layout.item_sample_a

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder {
        return TextViewHolder(itemView, this)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
        if(item is BaseItem<*>) {
            (viewHolder as TextViewHolder).mTextView.text = item.value.toString()
        } else {
            (viewHolder as TextViewHolder).mTextView.text = item.toString()
        }
    }

    override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {

    }
}