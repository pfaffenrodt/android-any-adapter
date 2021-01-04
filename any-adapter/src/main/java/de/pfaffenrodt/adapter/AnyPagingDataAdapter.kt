package de.pfaffenrodt.adapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ConcatAdapter
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import java.util.ArrayList

class AnyPagingDataAdapter: PagingDataAdapter<Any, ViewHolder> {

    companion object {
        private val TAG = "AnyPagingDataAdapter"
        protected val DEBUG = false
    }

    private var mPresenterSelector: PresenterSelector
    private val mPresenters = ArrayList<Presenter>()
    private val mViewTypePresenterMap = SparseArray<Presenter>()

    constructor(presenter: Presenter,
                diffCallback: DiffCallback<Any> = SimpleDiffCallback(),
                mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
                workerDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) : super(diffCallback.toItemCallback(), mainDispatcher, workerDispatcher) {
        mPresenterSelector = SinglePresenterSelector(presenter)
    }

    constructor(presenterSelector: PresenterSelector,
                diffCallback: DiffCallback<Any> = SimpleDiffCallback(),
                mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
                workerDispatcher: CoroutineDispatcher = Dispatchers.Default
    ): super(diffCallback.toItemCallback(), mainDispatcher, workerDispatcher) {
        mPresenterSelector = presenterSelector
    }

    constructor(diffCallback: DiffCallback<Any> = SimpleDiffCallback(),
                mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
                workerDispatcher: CoroutineDispatcher = Dispatchers.Default
    ) : this(NoPresenter(), diffCallback, mainDispatcher, workerDispatcher)


    /**
     * Returns the number of items in the adapter.
     */
    fun size(): Int {
        return this.itemCount
    }

    fun get(position: Int) : Any? {
        return this.getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        val item = get(position)
        val presenter = getPresenter(item)
        var type = mPresenters.indexOf(presenter)
        if (type < 0) {
            mPresenters.add(presenter)
            type = mPresenters.indexOf(presenter)
            if (DEBUG) Log.v(TAG, "getItemViewType added presenter $presenter type $type")

            onAddPresenter(presenter, type)
        }
        return type
    }

    fun getPresenter(item: Any?): Presenter {
        item ?: return NoPresenter()
        return mPresenterSelector.getPresenter(item)
    }

    protected fun onAddPresenter(presenter: Presenter, type: Int) {
        mViewTypePresenterMap.put(type, presenter)
    }

    protected fun getPresenterByViewType(viewType: Int): Presenter {
        return mViewTypePresenterMap.get(viewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return getPresenterByViewType(viewType).onCreateViewHolder(parent)
    }

    protected fun getPresenter(holder: ViewHolder): Presenter {
        if (holder is PresenterProvider) {
            return (holder as PresenterProvider).presenter
        }
        throw IllegalArgumentException(
                "ObjectAdapter require that ViewHolder implement PresenterProvider or inherit from BaseViewHolder"
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = get(position)?:return
        getPresenter(holder).onBindViewHolder(holder, item)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        getPresenter(holder).onUnbindViewHolder(holder)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        getPresenter(holder).onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        getPresenter(holder).onViewDetachedFromWindow(holder)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.PREPEND] [LoadState] as a list item at the end of the presented list.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeaderAndFooter
     * @see withLoadStateFooter
     */
    fun withLoadStateHeader(
        headerPresenter: Presenter
    ): ConcatAdapter {
        val headerAdapter = AnyLoadStateAdapter(headerPresenter)
        addLoadStateListener { loadStates ->
            headerAdapter.loadState = loadStates.prepend
        }
        return ConcatAdapter(headerAdapter, this)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.PREPEND] [LoadState] as a list item at the end of the presented list.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeaderAndFooter
     * @see withLoadStateFooter
     */
    fun withLoadStateHeader(
            headerPresenter: PresenterSelector
    ): ConcatAdapter {
        val headerAdapter = AnyLoadStateAdapter(headerPresenter)
        addLoadStateListener { loadStates ->
            headerAdapter.loadState = loadStates.prepend
        }
        return ConcatAdapter(headerAdapter, this)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.APPEND] [LoadState] as a list item at the start of the presented list.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeaderAndFooter
     * @see withLoadStateHeader
     */
    fun withLoadStateFooter(
            footerPresenter: Presenter
    ): ConcatAdapter {
        val footerAdapter = AnyLoadStateAdapter(footerPresenter)
        addLoadStateListener { loadStates ->
            footerAdapter.loadState = loadStates.append
        }
        return ConcatAdapter(this, footerAdapter)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.APPEND] [LoadState] as a list item at the start of the presented list.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeaderAndFooter
     * @see withLoadStateHeader
     */
    fun withLoadStateFooter(
            footerPresenter: PresenterSelector
    ): ConcatAdapter {
        val footerAdapter = AnyLoadStateAdapter(footerPresenter)
        addLoadStateListener { loadStates ->
            footerAdapter.loadState = loadStates.append
        }
        return ConcatAdapter(this, footerAdapter)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.PREPEND] and [LoadType.APPEND] [LoadState]s as list items at the start and end
     * respectively.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeader
     * @see withLoadStateFooter
     */
    fun withLoadStateHeaderAndFooter(
            headerPresenter: Presenter,
            footerPresenter: Presenter
    ): ConcatAdapter {
        val headerAdapter = AnyLoadStateAdapter(headerPresenter)
        val footerAdapter = AnyLoadStateAdapter(footerPresenter)
        addLoadStateListener { loadStates ->
            headerAdapter.loadState = loadStates.prepend
            footerAdapter.loadState = loadStates.append
        }
        return ConcatAdapter(headerAdapter, this, footerAdapter)
    }

    /**
     * Create a [ConcatAdapter] with the provided [AnyLoadStateAdapter]s displaying the
     * [LoadType.PREPEND] and [LoadType.APPEND] [LoadState]s as list items at the start and end
     * respectively.
     *
     * @see AnyLoadStateAdapter
     * @see withLoadStateHeader
     * @see withLoadStateFooter
     */
    fun withLoadStateHeaderAndFooter(
            headerPresenter: PresenterSelector,
            footerPresenter: PresenterSelector
    ): ConcatAdapter {
        val headerAdapter = AnyLoadStateAdapter(headerPresenter)
        val footerAdapter = AnyLoadStateAdapter(footerPresenter)
        addLoadStateListener { loadStates ->
            headerAdapter.loadState = loadStates.prepend
            footerAdapter.loadState = loadStates.append
        }
        return ConcatAdapter(headerAdapter, this, footerAdapter)
    }
}