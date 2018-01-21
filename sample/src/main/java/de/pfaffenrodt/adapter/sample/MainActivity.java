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
package de.pfaffenrodt.adapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.pfaffenrodt.adapter.ArrayObjectAdapter;
import de.pfaffenrodt.adapter.BaseViewHolder;
import de.pfaffenrodt.adapter.ClassPresenterSelector;
import de.pfaffenrodt.adapter.DataBindingPresenter;
import de.pfaffenrodt.adapter.EventHandler;
import de.pfaffenrodt.adapter.Presenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();
        classPresenterSelector.addClassPresenter(
                String.class,
                new SamplePresenterA()
        );
        classPresenterSelector.addClassPresenter(
                Integer.class,
                new SamplePresenterB()
        );
        classPresenterSelector.addClassPresenter(
                SampleItem.class,
                new DataBindingPresenter(
                        R.layout.item_sample_databinding,
                        BR.item
                ).bindVariable(BR.eventHandler, mEventHandler)
        );

        ArrayList<Object> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            if(i % 2 == 0){
                items.add(i);
            }
            items.add("test "+i);
            items.add(new SampleItem("fooo"+i));
        }
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(classPresenterSelector);
        adapter.setItems(items, null);
        recyclerView.setAdapter(adapter);
    }


    class SamplePresenterA extends Presenter{
        @Override
        public int getLayoutId() {
            return R.layout.item_sample_a;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(View itemView, ViewGroup parent) {
            return new TextViewHolder(itemView, this);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object item) {
            ((TextViewHolder)viewHolder).mTextView.setText(item.toString());
        }

        @Override
        public void onUnbindViewHolder(RecyclerView.ViewHolder viewHolder) {

        }
    }

    class SamplePresenterB extends SamplePresenterA{
        @Override
        public int getLayoutId() {
            return R.layout.item_sample_b;
        }
    }

    class TextViewHolder extends BaseViewHolder{
        TextView mTextView;

        public TextViewHolder(View itemView, Presenter presenter) {
            super(itemView, presenter);
            mTextView = itemView.findViewById(R.id.text);
        }
    }

    private final EventHandler mEventHandler = new EventHandler() {
        @Override
        public void onEvent(View view, Object item) {
            Log.i("EventHandler", "onEvent: " + item);
        }
    };
}
