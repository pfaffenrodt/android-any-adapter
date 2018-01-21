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
package de.pfaffenrodt.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Base class for an Adapter.  Provides access to a data model and is
 * decoupled from the presentation of the items via {@link PresenterSelector}.
 */
public abstract class ObjectAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final String TAG = "ObjectAdapter";
    protected static final boolean DEBUG = false;

    PresenterSelector mPresenterSelector;
    private ArrayList<Presenter> mPresenters = new ArrayList<Presenter>();
    private final SparseArray<Presenter> mViewTypePresenterMap = new SparseArray<>();

    public ObjectAdapter(Presenter presenter) {
        super();
        if (presenter == null) {
            throw new IllegalStateException("Presenter must not be null");
        }
        mPresenterSelector = new SinglePresenterSelector(presenter);
    }

    public ObjectAdapter(PresenterSelector presenterSelector){
        if (presenterSelector == null) {
            throw new IllegalStateException("Presenter selector must not be null");
        }
        mPresenterSelector = presenterSelector;
    }

    public ObjectAdapter() {
    }

    @Deprecated
    public Object getItem(int position) {
        return get(position);
    }

    public abstract Object get(int position);

    @Override
    public int getItemViewType(int position) {
        Object item = get(position);
        Presenter presenter = getPresenter(item);
        int type = mPresenters.indexOf(presenter);
        if (type < 0) {
            mPresenters.add(presenter);
            type = mPresenters.indexOf(presenter);
            if (DEBUG) Log.v(TAG, "getItemViewType added presenter " + presenter + " type " + type);

            onAddPresenter(presenter, type);
        }
        return type;
    }

    public final Presenter getPresenter(Object item) {
        if (mPresenterSelector == null) {
            throw new IllegalStateException("Presenter selector must not be null");
        }
        return mPresenterSelector.getPresenter(item);
    }

    protected void onAddPresenter(Presenter presenter, int type) {
        mViewTypePresenterMap.put(type, presenter);
    }

    protected Presenter getPresenterByViewType(int viewType) {
        return mViewTypePresenterMap.get(viewType);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getPresenterByViewType(viewType).onCreateViewHolder(parent);
    }

    protected Presenter getPresenter(ViewHolder holder) {
        if(holder instanceof PresenterProvider) {
            return ((PresenterProvider) holder).getPresenter();
        }
        throw new IllegalArgumentException(
                "ObjectAdapter require that ViewHolder implement PresenterProvider or inherit from BaseViewHolder"
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object item = get(position);
        getPresenter(holder).onBindViewHolder(holder, item);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        getPresenter(holder).onUnbindViewHolder(holder);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        getPresenter(holder).onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        getPresenter(holder).onViewDetachedFromWindow(holder);
    }
}
