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

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import de.pfaffenrodt.adapter.ArrayObjectAdapter
import de.pfaffenrodt.adapter.ClassPresenterSelector
import de.pfaffenrodt.adapter.DataBindingPresenter
import de.pfaffenrodt.adapter.ObjectAdapter
import de.pfaffenrodt.adapter.Presenter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val classPresenterSelector = ClassPresenterSelector()
        classPresenterSelector.addClassPresenter(
                String::class,
                SamplePresenterA()
        )
        classPresenterSelector.addClassPresenter(
                Integer::class,
                SamplePresenterB()
        )
        classPresenterSelector.addClassPresenter(
                SampleItem::class,
                DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
        )

        val items = ArrayList<Any>()
        for (i in 0..49) {
            if (i % 2 == 0) {
                items.add(i)
            }
            items.add("test " + i)
            items.add(SampleItem("fooo" + i))
        }
        val items2 = (0..49).map { SampleItem("fooo" + it) }

        val adapter = ArrayObjectAdapter(classPresenterSelector)
        adapter.setItems(items)
        adapter.addAll(0, items2)
        recyclerView.adapter = adapter
    }


    internal open inner class SamplePresenterA : Presenter() {
        override val layoutId: Int
            get() = R.layout.item_sample_a

        override fun onCreateViewHolder(itemView: View, parent: ViewGroup): ObjectAdapter.ViewHolder {
            return TextViewHolder(itemView, this)
        }

        override fun onBindViewHolder(viewHolder: ObjectAdapter.ViewHolder, item: Any) {
            (viewHolder as TextViewHolder).mTextView.text = item.toString()
        }

        override fun onUnbindViewHolder(viewHolder: ObjectAdapter.ViewHolder) {

        }
    }

    internal inner class SamplePresenterB : SamplePresenterA() {
        override val layoutId: Int
            get() = R.layout.item_sample_b
    }

    internal inner class TextViewHolder(itemView: View, presenter: Presenter)
        : ObjectAdapter.ViewHolder(itemView, presenter) {
        var mTextView: TextView = itemView.findViewById<View>(R.id.text) as TextView
    }
}
