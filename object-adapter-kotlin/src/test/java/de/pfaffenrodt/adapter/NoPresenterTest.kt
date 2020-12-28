package de.pfaffenrodt.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockito_kotlin.mock

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NoPresenterTest{
    private val givenPresenter = NoPresenter()

    @Test
    fun it_provide_layoutId() {
        assertThat(givenPresenter.layoutId).isEqualTo(R.layout.no_presenter_layout)
    }

    @Test
    fun viewHolder_provides_presenter() {
        val mockedView: View = mock()
        val mockedContainer: ViewGroup = mock()
        val viewHolder = givenPresenter.onCreateViewHolder(mockedView, mockedContainer)
        assertThat(viewHolder).isInstanceOf(PresenterProvider::class.java)
        val presenterProvider : PresenterProvider = viewHolder as PresenterProvider
        assertThat(presenterProvider.presenter).isEqualTo(givenPresenter)
    }
}