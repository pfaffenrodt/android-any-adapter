package de.pfaffenrodt.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Presenter support callback of clicking item
 */
public abstract class ClickablePresenter extends Presenter {
    private EventHandler mClickHandler;

    public ClickablePresenter(EventHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setClickHandler(EventHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Binds a {@link View} to an item.
     */
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final Object item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickHandler.onEvent(viewHolder.itemView, item);
            }
        });
    }

    /**
     * Unbinds a {@link View} from an item. Any expensive references may be
     * released here, and any fields that are not bound for every item should be
     * cleared here.
     */
    public void onUnbindViewHolder(RecyclerView.ViewHolder viewHolder){
        viewHolder.itemView.setOnClickListener(null);
    }
}
