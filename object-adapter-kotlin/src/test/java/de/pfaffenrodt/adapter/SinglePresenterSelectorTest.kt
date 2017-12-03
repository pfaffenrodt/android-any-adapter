package de.pfaffenrodt.adapter

import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.*

internal class SinglePresenterSelectorTest: Spek({
    given( "a SinglePresenterSelector and a Presenter") {
        val presenter : Presenter = mock()
        val wrongPresenter : Presenter = mock()
        val instance = SinglePresenterSelector(presenter)
        it("should only have one presenter") {
            assertEquals(1 ,instance.presenters.size)
        }
        it("should only have that given presenter") {
            assertEquals(presenter, instance.presenters[0])
            assertNotEquals(wrongPresenter, instance.presenters[0])
        }
    }
})