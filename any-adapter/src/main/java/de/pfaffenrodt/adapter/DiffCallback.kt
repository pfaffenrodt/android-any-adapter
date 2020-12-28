/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.pfaffenrodt.adapter

/**
 * Callback that informs [ArrayAnyAdapter] how to compute list updates when using
 * [android.support.v7.util.DiffUtil] in [ArrayAnyAdapter.setItems] method.
 *
 *
 * The [ArrayAnyAdapter.setItems] method will pass items from different
 * lists to this callback in order to implement
 * the [android.support.v7.util.DiffUtil.Callback] it uses to compute differences between
 * lists.
 *
 * @param <Value> Type of items to compare.
</Value> */
abstract class DiffCallback<in Value> {
    /**
     * Called to decide whether two objects represent the same item.
     *
     * @param oldItem The item in the old list.
     * @param newItem The item in the new list.
     * @return True if the two items represent the same object or false if they are different.
     * @see android.support.v7.util.DiffUtil.Callback.areItemsTheSame
     */
    abstract fun areItemsTheSame(oldItem: Value, newItem: Value): Boolean

    /**
     * Called to decide whether two items have the same data. This information is used to detect if
     * the contents of an item have changed.
     *
     * @param oldItem The item in the old list.
     * @param newItem The item in the new list.
     * @return True if the contents of the items are the same or false if they are different.
     * @see android.support.v7.util.DiffUtil.Callback.areContentsTheSame
     */
    abstract fun areContentsTheSame(oldItem: Value, newItem: Value): Boolean

    /**
     * Called to get a change payload between an old and new version of an item.
     *
     * @see android.support.v7.util.DiffUtil.Callback.getChangePayload
     */
    open fun getChangePayload(oldItem: Value, newItem: Value): Any? {
        return null
    }
}

/**
 * Wrap DiffCallback. check if old and new items are instance of T
 */
inline fun <reified T> DiffCallback<T>.toAnyTypedDiffCallback() : DiffCallback<Any> {
    val wrappedCallback: DiffCallback<T> = this
    return object : DiffCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if(oldItem is T && newItem is T) {
                return wrappedCallback.areItemsTheSame(oldItem, newItem)
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            if(oldItem is T && newItem is T) {
                return wrappedCallback.areContentsTheSame(oldItem, newItem)
            }
            return false
        }

        override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
            if(oldItem is T && newItem is T) {
                return wrappedCallback.getChangePayload(oldItem, newItem)
            }
            return null
        }
    }
}