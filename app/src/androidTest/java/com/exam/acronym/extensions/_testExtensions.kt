package com.exam.acronym.extensions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers.not

fun Int.onId() = onView(ViewMatchers.withId(this))

fun Int.isDisplayed(): ViewInteraction =
    this.onId().check(matches(ViewMatchers.isDisplayed()))

fun Int.isNotDisplayed(): ViewInteraction =
    this.onId().check(matches(not(ViewMatchers.isDisplayed())))

fun Int.hasText(text: String): ViewInteraction =
    this.onId().check(matches(ViewMatchers.withText(text)))

fun Int.performClick() {
    this.onId().perform(ViewActions.click())
}

fun Int.type(text: String) {
    this.onId().perform(ViewActions.clearText(), ViewActions.typeText(text))
}