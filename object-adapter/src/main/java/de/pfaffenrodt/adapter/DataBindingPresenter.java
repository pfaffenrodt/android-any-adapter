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

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
public class DataBindingPresenter extends Presenter{

    private final int mLayoutId;
    private final int mBindingVariableId;
    private final SparseArrayCompat<Object> mBindMap;

    public DataBindingPresenter(int layoutId, int bindingVariableId) {
        this(layoutId, bindingVariableId,  null);
    }

    public DataBindingPresenter(
            int layoutId,
            int bindingVariableId,
            SparseArrayCompat<Object> bindMap) {
        mLayoutId = layoutId;
        mBindingVariableId = bindingVariableId;
        if(bindMap == null) {
            mBindMap = new SparseArrayCompat<>();
        } else {
            mBindMap = bindMap;
        }
    }

    public DataBindingPresenter bindVariable(int bindingVariableId, Object eventHandler) {
        mBindMap.put(bindingVariableId, eventHandler);
        return this;
    }

    public void unbindVariable(int bindingVariableId) {
        mBindMap.remove(bindingVariableId);
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View itemView, ViewGroup parent) {
        return new ViewHolder(DataBindingUtil.bind(itemView));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object item) {
        ViewHolder bindingViewHolder = (ViewHolder) viewHolder;
        onBindItem(bindingViewHolder.mBinding, item);
        onBindGlobalElements(bindingViewHolder.mBinding);
        bindingViewHolder.mBinding.executePendingBindings();
    }

    protected void onBindItem(ViewDataBinding binding, Object item) {
        binding.setVariable(mBindingVariableId, item);
    }

    protected void onBindGlobalElements(ViewDataBinding binding) {
        for (int i = 0; i < mBindMap.size(); i++) {
            int eventBindingVariableId = mBindMap.keyAt(i);
            Object eventHandler = mBindMap.valueAt(i);
            onBindGlobalElement(
                    binding,
                    eventBindingVariableId,
                    eventHandler
            );
        }
    }

    protected void onBindGlobalElement(ViewDataBinding binding,
                                       int bindingVariableId,
                                       Object globalElement) {
        if(bindingVariableId != -1 && globalElement != null) {
            binding.setVariable(bindingVariableId, globalElement);
        }
    }

    @Override
    public void onUnbindViewHolder(RecyclerView.ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        ViewHolder bindingViewHolder = (ViewHolder) viewHolder;
        bindingViewHolder.mBinding.unbind();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PresenterProvider{
        ViewDataBinding mBinding;
        public ViewHolder(ViewDataBinding binding){
            super(binding.getRoot());
            mBinding = binding;
        }

        @Override
        public Presenter getPresenter() {
            return DataBindingPresenter.this;
        }
    }
}
