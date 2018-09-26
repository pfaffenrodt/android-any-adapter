package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockito_kotlin.mock

import org.junit.jupiter.api.Assertions.*

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object NoPresenterTest : Spek({

    Feature("NoPresenter") {
        val noPresenter = NoPresenter()
        var viewHolder : RecyclerView.ViewHolder? = null

        Scenario("Default") {
            Given("A NoPresenter") {
                noPresenter
            }
            Then("should return id of no_presenter_layout") {
                assertEquals(R.layout.no_presenter_layout, noPresenter.layoutId)
            }
            When("create a ViewHolder") {
                val mockedView: View = mock()
                val mockedContainer: ViewGroup = mock()
                viewHolder = noPresenter.onCreateViewHolder(mockedView, mockedContainer)
            }
            Then(" it should have reference to NoPresenter") {
                assertTrue(viewHolder is PresenterProvider)
                val presenterProvider : PresenterProvider = viewHolder as PresenterProvider
                assertEquals(noPresenter, presenterProvider.presenter)
            }
        }
   }
})