package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

import org.junit.Assert.*

class NoPresenterTest : Spek({
    given("a NoPresenter") {

        val noPresenter = NoPresenter()

        it("should return id of no_presenter_layout") {
            assertEquals(R.layout.no_presenter_layout, noPresenter.layoutId)
        }
        on("create ViewHolder") {
            val mockedView : View = mock()
            val mockedContainer : ViewGroup = mock()
            val viewHolder = noPresenter.onCreateViewHolder(mockedView, mockedContainer)
            it("should have reference to NoPresenter") {
                assertEquals(noPresenter, viewHolder.presenter)
            }
        }
   }
})