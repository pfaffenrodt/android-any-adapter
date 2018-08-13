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
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.pfaffenrodt.adapter.*

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val classPresenterSelector = ClassPresenterSelector()
        classPresenterSelector.addClassPresenter(
                SampleItemA::class,
                SamplePresenterA()
        )
        classPresenterSelector.addClassPresenter(
                SampleItemB::class,
                SamplePresenterB()
        )
        classPresenterSelector.addClassPresenter(
                SampleItemC::class,
                DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
        )

        val items = ArrayList<BaseItem<*>>()
        for (i in 0..49) {
            when {
                i % 2 == 0 -> items.add(SampleItemA(i, "fo" +i))
                i % 3 == 0 -> items.add(SampleItemB(i, i))
                else -> items.add(SampleItemC(i, "fooo" + i))
            }
        }

        val adapter = ArrayAnyAdapter(classPresenterSelector)
        adapter.setItems(items)
        recyclerView.adapter = adapter
        Handler()
            .postDelayed (
            {
                for(i in 0 until items.size) {
                    when {
                        i % 6 == 0 -> items[i] = SampleItemC(i, "baar" + i)
                        i % 4 == 0 -> items[i] = SampleItemA(i, "baar" + i)
                    }
                }
                items.removeAt(7)
                items.removeAt(1)
                items.removeAt(4)
                adapter.setItems(items, SampleDiffCallback().toAnyTypedDiffCallback())
            },
            1000
        )
    }


    internal open inner class SamplePresenterA : Presenter() {
        override val layoutId: Int
            get() = R.layout.item_sample_a

        override fun onCreateViewHolder(itemView: View, parent: ViewGroup): RecyclerView.ViewHolder {
            return TextViewHolder(itemView, this)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any) {
            if(item is BaseItem<*>) {
                (viewHolder as TextViewHolder).mTextView.text = item.value.toString()
            } else {
                (viewHolder as TextViewHolder).mTextView.text = item.toString()
            }
        }

        override fun onUnbindViewHolder(viewHolder: RecyclerView.ViewHolder) {

        }
    }

    internal inner class SamplePresenterB : SamplePresenterA() {
        override val layoutId: Int
            get() = R.layout.item_sample_b
    }

    internal inner class TextViewHolder(itemView: View, presenter: Presenter)
        : BaseViewHolder(itemView, presenter) {
        var mTextView: TextView = itemView.findViewById<View>(R.id.text) as TextView
    }
}

class SampleDiffCallback : DiffCallback<BaseItem<*>>() {
    override fun areItemsTheSame(oldItem: BaseItem<*>, newItem: BaseItem<*>): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BaseItem<*>, newItem: BaseItem<*>): Boolean {
        return oldItem.value == newItem.value
    }
}
