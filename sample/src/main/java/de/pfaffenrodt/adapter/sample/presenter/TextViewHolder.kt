package de.pfaffenrodt.adapter.sample.presenter

import android.view.View
import android.widget.TextView
import de.pfaffenrodt.adapter.BaseViewHolder
import de.pfaffenrodt.adapter.Presenter
import de.pfaffenrodt.adapter.sample.R

class TextViewHolder(itemView: View, presenter: Presenter)
    : BaseViewHolder(itemView, presenter) {
    var mTextView: TextView = itemView.findViewById<View>(R.id.text) as TextView
}