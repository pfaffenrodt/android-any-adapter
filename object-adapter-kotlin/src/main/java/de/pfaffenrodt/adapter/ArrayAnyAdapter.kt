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

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.util.Log
import java.util.*

/**
 * An [AnyAdapter] implemented with an [ArrayList].
 */
open class ArrayAnyAdapter : AnyAdapter {

    companion object {
        private val DEBUG = false
        private val TAG = "ArrayAnyAdapter"
    }

    protected val mItems = ArrayList<Any>()
    // To compute the payload correctly, we should use a temporary list to hold all the old items.
    private val mOldItems = ArrayList<Any>()

    /**
     * Constructs an adapter with the given [PresenterSelector].
     */
    constructor(presenterSelector: PresenterSelector) : super(presenterSelector)

    /**
     * Constructs an adapter that uses the given [Presenter] for all items.
     */
    constructor(presenter: Presenter) : super(presenter)

    /**
     * Constructs an adapter.
     */
    constructor() : super()

    override fun getItemCount(): Int {
        return mItems.size
    }

    protected fun positionIsInRange(position: Int): Boolean {
       return position in 0..(itemCount - 1)
    }

    override fun get(position: Int): Any {
        return if (positionIsInRange(position)) {
            mItems[position]
        } else Unit
    }

    /**
     * Returns the index for the first occurrence of item in the adapter, or -1 if
     * not found.
     *
     * @param item The item to find in the list.
     * @return Index of the first occurrence of the item in the adapter, or -1
     * if not found.
     */
    fun indexOf(item: Any): Int {
        return mItems.indexOf(item)
    }

    /**
     * Notify that the content of a range of items changed. Note that this is
     * not same as items being added or removed.
     *
     * @param positionStart The position of first item that has changed.
     * @param itemCount     The count of how many items have changed.
     */
    fun notifyArrayItemRangeChanged(positionStart: Int, itemCount: Int) {
        notifyItemRangeChanged(positionStart, itemCount)
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
     * If the index is > [.getItemCount] an exception will be thrown.
     *
     * @param index The index at which the item should be inserted.
     * @param item  The item to insert into the adapter.
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
     * Moved the item at fromPosition to toPosition.
     *
     * @param fromPosition Previous position of the item.
     * @param toPosition   New position of the item.
     */
    fun move(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) {
            // no-op
            return
        }
        val item = mItems.removeAt(fromPosition)
        mItems.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    /**
     * Replaces item at position with a new item and calls notifyItemRangeChanged()
     * at the given position.  Note that this method does not compare new item to
     * existing item.
     *
     * @param position The index of item to replace.
     * @param item     The new item to be placed at given position.
     */
    fun replace(position: Int, item: Any) {
        if (!positionIsInRange(position)) {
            throw IllegalArgumentException("no item inside of ArrayAnyAdapter")
        }
        mItems[position] = item
        notifyItemRangeChanged(position, 1)
    }

    /**
     * Removes a range of items from the adapter. The range is specified by giving
     * the starting position and the number of elements to remove.
     *
     * @param position The index of the first item to remove.
     * @param count    The number of items to remove.
     * @return The number of items removed.
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

    /**
     * Set a new item list to adapter. The DiffUtil will compute the difference and dispatch it to
     * specified position.
     *
     * @param itemList List of new Items
     * @param callback Optional DiffCallback Object to compute the difference between the old data
     * set and new data set. When null, [.notifyDataSetChanged] will be fired.
     */
    fun setItems(itemList: List<Any>, callback: DiffCallback<Any>? = null) {
        if (callback == null) {
            // shortcut when DiffCallback is not provided
            mItems.clear()
            mItems.addAll(itemList)
            notifyDataSetChanged()
            return
        }
        mOldItems.clear()
        mOldItems.addAll(mItems)
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return mOldItems.size
            }

            override fun getNewListSize(): Int {
                return itemList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areItemsTheSame(mOldItems[oldItemPosition],
                        itemList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return callback.areContentsTheSame(mOldItems[oldItemPosition],
                        itemList[newItemPosition])
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return callback.getChangePayload(mOldItems[oldItemPosition],
                        itemList[newItemPosition])
            }
        })
        // update items.
        mItems.clear()
        mItems.addAll(itemList)
        // dispatch diff result
        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                if (DEBUG) {
                    Log.d(TAG, "onInserted")
                }
                notifyItemRangeInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                if (DEBUG) {
                    Log.d(TAG, "onRemoved")
                }
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                if (DEBUG) {
                    Log.d(TAG, "onMoved")
                }
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                if (DEBUG) {
                    Log.d(TAG, "onChanged")
                }
                notifyItemRangeChanged(position, count, payload)
            }
        })
    }
}