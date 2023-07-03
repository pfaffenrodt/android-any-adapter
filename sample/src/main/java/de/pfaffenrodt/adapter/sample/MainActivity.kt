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
package de.pfaffenrodt.adapter.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import androidx.paging.rxjava3.flowable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.pfaffenrodt.adapter.*
import de.pfaffenrodt.adapter.sample.presenter.LoadingPresenter
import de.pfaffenrodt.adapter.sample.presenter.SamplePresenterSelector
import io.reactivex.rxjava3.core.Single
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    var retried = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = AnyPagingDataAdapter(
                presenterSelector = SamplePresenterSelector(),
                diffCallback = SimpleDiffCallback()
        )
        val footerPresenter = LoadingPresenter {
            retried = true
            adapter.retry()
        }
        recyclerView.adapter = adapter.withLoadStateFooter(footerPresenter)

        val pager = Pager(
                config = PagingConfig(pageSize = 30),
                initialKey = 0
        ) { SamplePagingSource() }
        pager.flowable
                .subscribe { pagingData -> adapter.submitData(lifecycle, pagingData) }
    }

    inner class SamplePagingSource: RxPagingSource<Int, Any>() {
        init {
        }

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Any>> {
            val pageIndex = params.key?: return Single.just(LoadResult.Error(IllegalArgumentException("required key for page")))
            return Single.just(params).delay(2, TimeUnit.SECONDS)
                    .map {
                        if (it.key == 4 && !retried) {
                            return@map LoadResult.Error(RuntimeException("error to show viewtype"))
                        }
                        return@map LoadResult.Page(page(it), prevKey = null, nextKey = pageIndex + 1)
                    }
        }

        fun page(params: LoadParams<Int>): List<Any> {
            val pageIndex = params.key ?: return emptyList()
            val first = pageIndex * params.loadSize
            val last = first + params.loadSize
            val items = ArrayList<BaseItem<*>>()
            for (i in first..last) {
                when {
                    i % 2 == 0 -> items.add(SampleItemA(i, "SampleItemA: fo" +i))
                    i % 3 == 0 -> items.add(SampleItemB(i, i))
                    i % 5 == 0 -> items.add(SampleItemD(i, "SampleItemD: " + i))
                    else -> items.add(SampleItemC(i, "SampleItemC: fooo" + i))
                }
            }
            return items
        }

        override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
            return state.anchorPosition
        }
    }
}
