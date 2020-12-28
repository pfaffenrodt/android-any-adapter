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

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Presenter used with dataBinding.
 *
 * <pre>
 * usage:
 * new DataBindingPresenter(R.layout.item_sample, BR.item)
 *
 * item_sample.xml:
 * <layout>
 *     <data>
 *         <variable
 *          name="item"
 *          type="de.pfaffenrodt.adapter.sample.SampleItem" />
 *     </data>
 *     ...
 * </layout>
 *
 * Databinding can be enabled in build.gradle.
 * android{
 *      dataBinding.enabled = true
 * }
 * </pre>
 */
open class DataBindingPresenter(
        override val layoutId: Int,
        private val mBindingVariableId: Int,
        private val mBindMap: SparseArrayCompat<Any> = SparseArrayCompat()
) : Presenter() {

    fun bindHandler(bindingVariableId: Int, eventHandler: (Any) -> Unit): DataBindingPresenter {
        return bindVariable(bindingVariableId, object: EventHandler {
            override fun onEvent(view: View, item: Any) {
                eventHandler(item)
            }
        })
    }

    fun bindVariable(bindingVariableId: Int, variable: Any): DataBindingPresenter {
        mBindMap.put(bindingVariableId, variable)
        return this
    }

    fun unbindVariable(bindingVariableId: Int) {
        mBindMap.remove(bindingVariableId)
    }

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): ViewHolder {
        return ViewHolder(DataBindingUtil.bind(itemView)!!, this)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
        val bindingViewHolder = viewHolder as ViewHolder
        onBindItem(bindingViewHolder.mBinding, item)
        onBindGlobalElements(bindingViewHolder.mBinding)
        bindingViewHolder.mBinding.executePendingBindings()
    }

    protected fun onBindItem(binding: ViewDataBinding, item: Any) {
        binding.setVariable(mBindingVariableId, item)
    }

    protected fun onBindGlobalElements(binding: ViewDataBinding) {
        for (i in 0 until mBindMap.size()) {
            val eventBindingVariableId = mBindMap.keyAt(i)
            val eventHandler = mBindMap.valueAt(i)
            onBindGlobalElement(
                    binding,
                    eventBindingVariableId,
                    eventHandler
            )
        }
    }

    protected open fun onBindGlobalElement(binding: ViewDataBinding,
                                      bindingVariableId: Int,
                                      globalElement: Any?) {
        if (bindingVariableId != -1 && globalElement != null) {
            binding.setVariable(bindingVariableId, globalElement)
        }
    }

    override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        super.onUnbindViewHolder(viewHolder)
        val bindingViewHolder = viewHolder as ViewHolder
        bindingViewHolder.mBinding.unbind()
    }

    inner class ViewHolder(
        internal var mBinding: ViewDataBinding,
        override val presenter: Presenter
    ): RecyclerView.ViewHolder(mBinding.root), PresenterProvider
}

