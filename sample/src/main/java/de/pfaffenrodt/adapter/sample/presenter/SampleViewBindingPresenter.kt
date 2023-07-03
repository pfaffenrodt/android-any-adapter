package de.pfaffenrodt.adapter.sample.presenter

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import de.pfaffenrodt.adapter.ViewBindingPresenter
import de.pfaffenrodt.adapter.sample.R
import de.pfaffenrodt.adapter.sample.databinding.ItemSampleViewbindingBinding

class SampleViewBindingPresenter(
    val bindItem: (ItemSampleViewbindingBinding, Any) -> Unit
): ViewBindingPresenter() {
    override val layoutId: Int = R.layout.item_sample_viewbinding
    override fun createViewBinding(itemView: View, parent: ViewGroup): ViewBinding {
        return ItemSampleViewbindingBinding.bind(itemView)
    }

    override fun onBindItem(binding: ViewBinding, item: Any) {
        val itemBinding = binding as ItemSampleViewbindingBinding
        bindItem(itemBinding, item)
    }
}