/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.pfaffenrodt.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.View
import android.view.ViewGroup

/**
 * Presenter used with dataBinding.
 *
 * <pre>
 * usage:
 * new DataBindingPresenter(R.layout.item_sample, BR.item)
 *
 * item_sample.xml:
 * <layout>
 * <data>
 * <variable name="item" type="de.pfaffenrodt.adapter.sample.SampleItem"></variable>
</data> *
 * ...
</layout> *
 *
 * Databinding can be enabled in build.gradle.
 * android{
 * dataBinding.enabled = true
 * }
</pre> *
 */
class DataBindingPresenter(override val layoutId: Int, private val mBindingVariableId: Int) : Presenter() {

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): ViewHolder {
        return ViewHolder(DataBindingUtil.bind(itemView), this)
    }

    override fun onBindViewHolder(viewHolder: AnyAdapter.ViewHolder, item: Any) {
        val bindingViewHolder = viewHolder as ViewHolder
        onBindItem(bindingViewHolder.mBinding, item)
        bindingViewHolder.mBinding.executePendingBindings()
    }

    protected fun onBindItem(binding: ViewDataBinding, item: Any) {
        binding.setVariable(mBindingVariableId, item)
    }

    override fun onUnbindViewHolder(viewHolder: AnyAdapter.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        val bindingViewHolder = viewHolder as ViewHolder
        bindingViewHolder.mBinding.unbind()
    }

    inner class ViewHolder(internal var mBinding: ViewDataBinding, presenter: Presenter) : AnyAdapter.ViewHolder(mBinding.root, presenter)
}
