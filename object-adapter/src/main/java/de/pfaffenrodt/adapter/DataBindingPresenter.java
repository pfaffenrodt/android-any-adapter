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

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
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

    public DataBindingPresenter(int layoutId, int bindingVariableId) {
        this.mLayoutId = layoutId;
        mBindingVariableId = bindingVariableId;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(View itemView, ViewGroup parent) {
        return new ViewHolder(DataBindingUtil.bind(itemView), this);
    }

    @Override
    public void onBindViewHolder(ObjectAdapter.ViewHolder viewHolder, Object item) {
        ViewHolder bindingViewHolder = (ViewHolder) viewHolder;
        onBindItem(bindingViewHolder.mBinding, item);
        bindingViewHolder.mBinding.executePendingBindings();
    }

    protected void onBindItem(ViewDataBinding binding, Object item) {
        binding.setVariable(mBindingVariableId, item);
    }

    @Override
    public void onUnbindViewHolder(ObjectAdapter.ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        ViewHolder bindingViewHolder = (ViewHolder) viewHolder;
        bindingViewHolder.mBinding.unbind();
    }

    public class ViewHolder extends ObjectAdapter.ViewHolder{
        ViewDataBinding mBinding;
        public ViewHolder(ViewDataBinding binding, Presenter presenter){
            super(binding.getRoot(), presenter);
            mBinding = binding;
        }
    }
}
