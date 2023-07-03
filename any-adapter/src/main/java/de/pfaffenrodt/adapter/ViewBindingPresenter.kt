package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingPresenter: Presenter() {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            createViewBinding(itemView, parent),
            this,
        )
    }

    abstract fun createViewBinding(itemView: View, parent: ViewGroup): ViewBinding

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
        val bindingViewHolder = viewHolder as ViewHolder
        onBindItem(bindingViewHolder.mBinding, item)
    }

    abstract fun onBindItem(binding: ViewBinding, item: Any)

    override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        val bindingViewHolder = viewHolder as ViewHolder
        onUnbindViewBinding(bindingViewHolder.mBinding)
    }

    open fun onUnbindViewBinding(binding: ViewBinding) {}

    inner class ViewHolder(
        internal var mBinding: ViewBinding,
        override val presenter: Presenter
    ): RecyclerView.ViewHolder(mBinding.root), PresenterProvider
}