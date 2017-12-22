package de.pfaffenrodt.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.view.View;

/**
 * Presenter used with dataBinding.
 * Provide callback of clicking item
 */
public class ClickableDataBindingPresenter extends DataBindingPresenter {

    protected EventHandler mClickHandler;

    public ClickableDataBindingPresenter(int layoutId,
                                         int bindingVariableId,
                                         EventHandler clickHandler) {
        this(layoutId, bindingVariableId, null, clickHandler);
    }

    public ClickableDataBindingPresenter(int layoutId,
                                         int bindingVariableId,
                                         SparseArrayCompat<Object> bindMap,
                                         EventHandler clickHandler) {
        super(layoutId, bindingVariableId, bindMap);
        mClickHandler = clickHandler;
    }

    public void setClickHandler(EventHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public void onBindViewHolder(final ObjectAdapter.ViewHolder viewHolder, final Object item) {
        super.onBindViewHolder(viewHolder, item);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickHandler.onEvent(viewHolder.itemView, item);
            }
        });
    }

    @Override
    public void onUnbindViewHolder(ObjectAdapter.ViewHolder viewHolder) {
        super.onUnbindViewHolder(viewHolder);
        viewHolder.itemView.setOnClickListener(null);
    }
}
