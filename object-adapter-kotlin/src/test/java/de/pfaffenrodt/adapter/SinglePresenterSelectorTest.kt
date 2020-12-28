package de.pfaffenrodt.adapter

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

class SinglePresenterSelectorTest {

    private val givenPresenter: Presenter = mock()
    private val givenPresenterSelector = SinglePresenterSelector(givenPresenter)
    private val wrongPresenter: Presenter = mock()


    @Test
    fun it_provide_correct_size_of_presenter()
    {
        assertThat(givenPresenterSelector.presenters.size).isEqualTo(1)
    }

    @Test
    fun it_provide_only_one_presenter() {
        assertThat(givenPresenterSelector.presenters[0])
                .isNotEqualTo(wrongPresenter)
        assertThat(givenPresenterSelector.presenters[0])
                .isEqualTo(givenPresenter)
    }
}

