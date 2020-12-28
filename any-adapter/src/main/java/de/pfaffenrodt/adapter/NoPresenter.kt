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

/**
 * Simple Placeholder if no Presenter matched.
 * Placeholder to support using not null properties.
 */
class NoPresenter : Presenter() {

    override val layoutId: Int
        get() = R.layout.no_presenter_layout

    override fun onCreateViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder {
        return BaseViewHolder(itemView, this)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {}
}