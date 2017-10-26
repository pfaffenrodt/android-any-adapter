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

import java.util.ArrayList

/**
 * An [ObjectAdapter] implemented with an [ArrayList].
 */
class ArrayObjectAdapter : ObjectAdapter {

    protected var mItems: ArrayList<Any> = ArrayList<Any>()

    constructor(presenter: Presenter) : super(presenter)

    constructor(presenter: Presenter, items: ArrayList<Any>) : super(presenter) {
        this.mItems = items
    }

    constructor(presenterSelector: PresenterSelector) : super(presenterSelector)

    constructor(presenterSelector: PresenterSelector, items: ArrayList<Any>) : super(presenterSelector) {
        this.mItems = items
    }

    protected fun positionIsInRange(position: Int): Boolean {
       return position in 0..(itemCount - 1)
    }

    override fun getItem(position: Int): Any {
        return if (positionIsInRange(position)) {
            mItems[position]
        } else Unit
    }

    /**
     * Returns the index for the first occurrence of item in the adapter, or -1 if
     * not found.
     *
     * @param item  The item to find in the list.
     * @return Index of the first occurrence of the item in the adapter, or -1
     * if not found.
     */
    fun indexOf(item: Any): Int {
        return mItems.indexOf(item)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    /**
     * Adds an item to the end of the adapter.
     *
     * @param item The item to add to the end of the adapter.
     */
    fun add(item: Any) {
        add(mItems.size, item)
    }

    /**
     * Inserts an item into this adapter at the specified index.
     * If the index is >= [.getItemCount] an exception will be thrown.
     *
     * @param index The index at which the item should be inserted.
     * @param item The item to insert into the adapter.
     */
    fun add(index: Int, item: Any) {
        mItems.add(index, item)
        notifyItemRangeInserted(index, 1)
    }

    /**
     * Adds the objects in the given collection to the adapter, starting at the
     * given index.  If the index is >= [.getItemCount] an exception will be thrown.
     *
     * @param index The index at which the items should be inserted.
     * @param items A [Collection] of items to insert.
     */
    fun addAll(index: Int, items: Collection<Any>) {
        val itemsCount = items.size
        if (itemsCount == 0) {
            return
        }
        mItems.addAll(index, items)
        notifyItemRangeInserted(index, itemsCount)
    }

    /**
     * Removes the first occurrence of the given item from the adapter.
     *
     * @param item The item to remove from the adapter.
     * @return True if the item was found and thus removed from the adapter.
     */
    fun remove(item: Any): Boolean {
        val index = mItems.indexOf(item)
        if (index >= 0) {
            mItems.removeAt(index)
            notifyItemRangeRemoved(index, 1)
        }
        return index >= 0
    }

    /**
     * Replaces item at position with a new item and calls notifyItemRangeChanged()
     * at the given position.  Note that this method does not compare new item to
     * existing item.
     * @param position  The index of item to replace.
     * @param item      The new item to be placed at given position.
     */
    fun replace(position: Int, item: Any) {
        if (!positionIsInRange(position)) {
            throw IllegalArgumentException("no item inside of ArrayObjectAdapter")
        }
        mItems[position] = item
        notifyItemRangeChanged(position, 1)
    }

    /**
     * Removes a range of items from the adapter. The range is specified by giving
     * the starting position and the number of elements to remove.
     *
     * @param position The index of the first item to remove.
     * @param count The number of items to remove.
     * @return The number of items removed. (no position will be -1)
     */
    fun removeItems(position: Int, count: Int): Int {
        val itemsToRemove = Math.min(count, mItems.size - position)
        if (itemsToRemove <= 0) {
            return 0
        }
        for (i in 0 until itemsToRemove) {
            mItems.removeAt(position)
        }
        notifyItemRangeRemoved(position, itemsToRemove)
        return itemsToRemove
    }

    /**
     * Removes all items from this adapter, leaving it empty.
     */
    fun clear() {
        val itemCount = mItems.size
        if (itemCount == 0) {
            return
        }
        mItems.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
}
