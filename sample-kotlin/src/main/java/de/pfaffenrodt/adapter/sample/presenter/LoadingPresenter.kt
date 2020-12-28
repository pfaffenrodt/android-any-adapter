package de.pfaffenrodt.adapter.sample.presenter

import de.pfaffenrodt.adapter.DataBindingPresenter
import de.pfaffenrodt.adapter.sample.BR
import de.pfaffenrodt.adapter.sample.R

open class LoadingPresenter(retryHandler: (item: Any) -> Unit) : DataBindingPresenter(
        R.layout.item_loading,
        BR.item
) {
    init {
        bindHandler(BR.eventHandler, retryHandler)
    }
}