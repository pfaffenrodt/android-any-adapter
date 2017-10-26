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
package de.pfaffenrodt.adapter;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An {@link ObjectAdapter} implemented with an {@link ArrayList}.
 */
public class ArrayObjectAdapter extends ObjectAdapter {
    public static final int NO_POSITION = -1;
    protected ArrayList<Object> mItems;
    public ArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public ArrayObjectAdapter(Presenter presenter, ArrayList<Object> items) {
        super(presenter);
        this.mItems = items;
    }

    public ArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public ArrayObjectAdapter(PresenterSelector presenterSelector, ArrayList<Object> items) {
        super(presenterSelector);
        this.mItems = items;
    }

    @Override
    public Object getItem(int position) {
        if(position >= 0 && position < getItemCount()){
            return mItems.get(position);
        }
        return null;
    }

    /**
     * Returns the index for the first occurrence of item in the adapter, or -1 if
     * not found.
     *
     * @param item  The item to find in the list.
     * @return Index of the first occurrence of the item in the adapter, or -1
     *         if not found.
     */
    public int indexOf(Object item) {
        return mItems.indexOf(item);
    }

    @Override
    public int getItemCount() {
        if(mItems != null){
            return mItems.size();
        }
        return 0;
    }

    private void itemsShouldNotBeNull() {
        if(mItems == null) {
            mItems = new ArrayList<>();
        }
    }

    /**
     * Adds an item to the end of the adapter.
     *
     * @param item The item to add to the end of the adapter.
     */
    public void add(Object item) {
        itemsShouldNotBeNull();
        add(mItems.size(), item);
    }
    /**
     * Inserts an item into this adapter at the specified index.
     * If the index is >= {@link #getItemCount} an exception will be thrown.
     *
     * @param index The index at which the item should be inserted.
     * @param item The item to insert into the adapter.
     */
    public void add(int index, Object item) {
        itemsShouldNotBeNull();
        mItems.add(index, item);
        notifyItemRangeInserted(index, 1);
    }
    /**
     * Adds the objects in the given collection to the adapter, starting at the
     * given index.  If the index is >= {@link #getItemCount} an exception will be thrown.
     *
     * @param index The index at which the items should be inserted.
     * @param items A {@link Collection} of items to insert.
     */
    public void addAll(int index, @NonNull Collection items) {
        int itemsCount = items.size();
        if (itemsCount == 0) {
            return;
        }
        itemsShouldNotBeNull();
        mItems.addAll(index, items);
        notifyItemRangeInserted(index, itemsCount);
    }

    /**
     * Removes the first occurrence of the given item from the adapter.
     *
     * @param item The item to remove from the adapter.
     * @return True if the item was found and thus removed from the adapter.
     */
    public boolean remove(Object item) {
        if(mItems == null) {
            return false;
        }
        int index = mItems.indexOf(item);
        if (index >= 0) {
            mItems.remove(index);
            notifyItemRangeRemoved(index, 1);
        }
        return index >= 0;
    }
    /**
     * Replaces item at position with a new item and calls notifyItemRangeChanged()
     * at the given position.  Note that this method does not compare new item to
     * existing item.
     * @param position  The index of item to replace.
     * @param item      The new item to be placed at given position.
     */
    public void replace(int position, Object item) {
        if(mItems == null || position >= mItems.size()) {
            throw new IllegalArgumentException("no item inside of ArrayObjectAdapter");
        }
        mItems.set(position, item);
        notifyItemRangeChanged(position, 1);
    }
    /**
     * Removes a range of items from the adapter. The range is specified by giving
     * the starting position and the number of elements to remove.
     *
     * @param position The index of the first item to remove.
     * @param count The number of items to remove.
     * @return The number of items removed. (no position will be -1)
     */
    public int removeItems(int position, int count) {
        if(mItems == null) {
            return NO_POSITION;
        }
        int itemsToRemove = Math.min(count, mItems.size() - position);
        if (itemsToRemove <= 0) {
            return 0;
        }
        for (int i = 0; i < itemsToRemove; i++) {
            mItems.remove(position);
        }
        notifyItemRangeRemoved(position, itemsToRemove);
        return itemsToRemove;
    }
    /**
     * Removes all items from this adapter, leaving it empty.
     */
    public void clear() {
        if(mItems == null) {
            return;
        }
        int itemCount = mItems.size();
        if (itemCount == 0) {
            return;
        }
        mItems.clear();
        notifyItemRangeRemoved(0, itemCount);
    }
}
