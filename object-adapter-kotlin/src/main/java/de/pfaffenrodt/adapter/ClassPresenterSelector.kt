/*
 * Copyright (C) 2017 Dimitri Pfaffenrodt
 * Copyright (C) 2014 The Android Open Source Project
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
package de.pfaffenrodt.adapter

import java.util.ArrayList
import java.util.HashMap
import kotlin.reflect.KClass

/**
 * A ClassPresenterSelector selects a [Presenter] based on the item's
 * Java class.
 */
class ClassPresenterSelector : PresenterSelector() {
    private val mPresenters = ArrayList<Presenter>()

    private val mClassMap = HashMap<Class<*>, Any>()

    override val presenters: Array<Presenter>
        get() = mPresenters.toTypedArray()

    /**
     * Sets a presenter to be used for the given class.
     * @param cls The data model class to be rendered.
     * @param presenter The presenter that renders the objects of the given class.
     * @return This ClassPresenterSelector object.
     */
    fun addClassPresenter(cls: KClass<*>, presenter: Presenter): ClassPresenterSelector {
        return addClassPresenter(cls.java, presenter)
    }

    /**
     * Sets a presenter to be used for the given class.
     * @param cls The data model class to be rendered.
     * @param presenter The presenter that renders the objects of the given class.
     * @return This ClassPresenterSelector object.
     */
    fun addClassPresenter(cls: Class<*>, presenter: Presenter): ClassPresenterSelector {
        mClassMap.put(cls, presenter)
        if (!mPresenters.contains(presenter)) {
            mPresenters.add(presenter)
        }
        return this
    }

    /**
     * Sets a presenter selector to be used for the given class.
     * @param cls The data model class to be rendered.
     * @param presenterSelector The presenter selector that finds the right presenter for a given
     * class.
     * @return This ClassPresenterSelector object.
     */
    fun addClassPresenterSelector(cls: KClass<*>,
                                  presenterSelector: PresenterSelector): ClassPresenterSelector {
        return addClassPresenterSelector(cls.java, presenterSelector)
    }

    /**
     * Sets a presenter selector to be used for the given class.
     * @param cls The data model class to be rendered.
     * @param presenterSelector The presenter selector that finds the right presenter for a given
     * class.
     * @return This ClassPresenterSelector object.
     */
    fun addClassPresenterSelector(cls: Class<*>,
                                  presenterSelector: PresenterSelector): ClassPresenterSelector {
        mClassMap.put(cls, presenterSelector)
        val innerPresenters = presenterSelector.presenters
        for (i in innerPresenters.indices)
            if (!mPresenters.contains(innerPresenters[i])) {
                mPresenters.add(innerPresenters[i])
            }
        return this
    }

    override fun getPresenter(item: Any): Presenter {
        var cls: Class<*>? = item.javaClass
        var presenter: Any?

        do {
            presenter = mClassMap[cls]
            if (presenter is PresenterSelector) {
                val innerPresenter = presenter.getPresenter(item)
                if (innerPresenter !is NoPresenter) {
                    return innerPresenter
                }
            }
            cls = cls?.superclass
        } while (presenter == null && cls != null)
        if(presenter == null) {
            presenter = NoPresenter()
        }
        return presenter as Presenter
    }
}
