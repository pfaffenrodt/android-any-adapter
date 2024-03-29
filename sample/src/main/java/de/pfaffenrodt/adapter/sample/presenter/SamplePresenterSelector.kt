package de.pfaffenrodt.adapter.sample.presenter

import de.pfaffenrodt.adapter.ClassPresenterSelector
import de.pfaffenrodt.adapter.DataBindingPresenter
import de.pfaffenrodt.adapter.sample.R
import de.pfaffenrodt.adapter.sample.BR
import de.pfaffenrodt.adapter.sample.SampleItemA
import de.pfaffenrodt.adapter.sample.SampleItemB
import de.pfaffenrodt.adapter.sample.SampleItemC
import de.pfaffenrodt.adapter.sample.SampleItemD

class SamplePresenterSelector: ClassPresenterSelector() {
    init {
        addClassPresenter(
                SampleItemA::class,
                SamplePresenterA()
        )
        addClassPresenter(
                SampleItemB::class,
                SamplePresenterB()
        )
        addClassPresenter(
                SampleItemC::class,
                DataBindingPresenter(R.layout.item_sample_databinding, BR.item)
        )
        addClassPresenter(
                SampleItemD::class,
                SampleViewBindingPresenter {
                    binding, item -> run {
                        val sampleItem = item as SampleItemD
                        binding.text.text = sampleItem.value
                    }
                },
        )
    }
}