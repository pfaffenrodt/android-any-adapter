/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 * Copyright (C) 2014 The Android Open Source Project
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

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A Presenter is used to generate [View]s and bind Objects to them on
 * demand. It is closely related to the concept of an [ ], but is
 * not position-based.  The leanback framework implements the adapter concept using
 * [AnyAdapter] which refers to a Presenter (or [PresenterSelector]) instance.
 *
 *
 *
 * Presenters should be stateless.  Presenters typically extend [AnyAdapter.ViewHolder] to store all
 * necessary view state information, such as references to child views to be used when
 * binding to avoid expensive calls to [View.findViewById].
 *
 *
 *
 *
 * A trivial Presenter that takes a string and renders it into a [ ]:
 *
 * <pre class="prettyprint">
 * public class StringTextViewPresenter extends Presenter {
 * // This class does not need a custom ViewHolder, since it does not use
 * // a complex layout.
 *
 * @Override
 * public ViewHolder onCreateViewHolder(ViewGroup parent) {
 * return new ViewHolder(new TextView(parent.getContext()));
 * }
 *
 * @Override
 * public void onBindViewHolder(ViewHolder viewHolder, Object item) {
 * String str = (String) item;
 * TextView textView = (TextView) viewHolder.mView;
 *
 * textView.setText(item);
 * }
 *
 * @Override
 * public void onUnbindViewHolder(ViewHolder viewHolder) {
 * // Nothing to unbind for TextView, but if this viewHolder had
 * // allocated bitmaps, they can be released here.
 * }
 * }
</pre> *
 */
abstract class Presenter {

    /**
     * @return id of layout that will be inflated in [.onCreateViewHolder] [.inflateItemLayout]
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Creates a new [View].
     */
    fun onCreateViewHolder(parent: ViewGroup): AnyAdapter.ViewHolder {
        val itemView = inflateItemLayout(parent)
        return onCreateViewHolder(itemView, parent)
    }

    abstract fun onCreateViewHolder(itemView: View, parent: ViewGroup): AnyAdapter.ViewHolder

    private fun inflateItemLayout(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
    }

    /**
     * Binds a [View] to an item.
     */
    abstract fun onBindViewHolder(viewHolder: AnyAdapter.ViewHolder, item: Any)

    /**
     * Unbinds a [View] from an item. Any expensive references may be
     * released here, and any fields that are not bound for every item should be
     * cleared here.
     */
    open fun onUnbindViewHolder(viewHolder: AnyAdapter.ViewHolder) {}

    /**
     * Called when a view created by this presenter has been attached to a window.
     *
     *
     * This can be used as a reasonable signal that the view is about to be seen
     * by the user. If the adapter previously freed any resources in
     * [.onViewDetachedFromWindow]
     * those resources should be restored here.
     *
     * @param holder Holder of the view being attached
     */
    fun onViewAttachedToWindow(holder: AnyAdapter.ViewHolder) {}

    /**
     * Called when a view created by this presenter has been detached from its window.
     *
     *
     * Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an presenter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.
     *
     * @param holder Holder of the view being detached
     */
    fun onViewDetachedFromWindow(holder: AnyAdapter.ViewHolder) {}

    /**
     * Called to set a click listener for the given view holder.
     *
     * The default implementation sets the click listener on the root view in the view holder.
     * If the root view isn't focusable this method should be overridden to set the listener
     * on the appropriate focusable child view(s).
     *
     * @param holder The view holder containing the view(s) on which the listener should be set.
     * @param listener The click listener to be set.
     */
    fun setOnClickListener(holder: AnyAdapter.ViewHolder, listener: View.OnClickListener) {
        holder.itemView.setOnClickListener(listener)
    }
}
