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

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

/**
 * Base class for an Adapter.  Provides access to a data model and is
 * decoupled from the presentation of the items via [PresenterSelector].
 */
abstract class ObjectAdapter : RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    companion object {
        private val TAG = "ObjectAdapter"
        protected val DEBUG = false
    }

    private var mPresenterSelector: PresenterSelector
    private val mPresenters = ArrayList<Presenter>()

    constructor(presenter: Presenter) : super() {
        mPresenterSelector = SinglePresenterSelector(presenter)
    }

    constructor(presenterSelector: PresenterSelector) {
        mPresenterSelector = presenterSelector
    }

    abstract fun getItem(position: Int) : Any

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val presenter = getPresenter(item)
        var type = mPresenters.indexOf(presenter)
        if (type < 0) {
            mPresenters.add(presenter)
            type = mPresenters.indexOf(presenter)
            if (DEBUG) Log.v(TAG, "getItemViewType added presenter $presenter type $type")

            onAddPresenter(presenter, type)
        }
        return type
    }

    fun getPresenter(item: Any): Presenter {
        return mPresenterSelector.getPresenter(item)
    }

    protected fun onAddPresenter(presenter: Presenter, type: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return mPresenterSelector.getPresenter(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.presenter.onBindViewHolder(holder, item)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.presenter.onUnbindViewHolder(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.presenter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.presenter.onViewDetachedFromWindow(holder)
    }

    open class ViewHolder(itemView: View, val presenter: Presenter) : RecyclerView.ViewHolder(itemView)
}
