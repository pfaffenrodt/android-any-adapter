package de.pfaffenrodt.adapter

import com.nhaarman.mockito_kotlin.mock

import org.junit.jupiter.api.Assertions.*

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object SinglePresenterSelectorTest: Spek({
    Feature("Single Presenter")  {
        Scenario("Default") {
            val presenter: Presenter = mock()
            val wrongPresenter: Presenter = mock()
            val instance = SinglePresenterSelector(presenter)
            Given("a SinglePresenterSelector and a Presenter") {
                assertTrue(presenter is Presenter)
                assertTrue(instance is SinglePresenterSelector)
            }
            Then("it should only have one presenter") {
                assertEquals(1, instance.presenters.size)
            }
            Then("it should only have that given presenter") {
                assertEquals(presenter, instance.presenters[0])
                assertNotEquals(wrongPresenter, instance.presenters[0])
            }
        }
    }
})

