package com.exam.acronym

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.exam.acronym.extensions.isDisplayed
import com.exam.acronym.extensions.isNotDisplayed
import com.exam.acronym.activities.MainActivity
import com.exam.acronym.extensions.performClick
import com.exam.acronym.extensions.type
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testViews() {
        ActivityScenario.launch(MainActivity::class.java)
        R.id.text_input.performClick()
        R.id.text_input.type("en")
        R.id.btn_search.performClick()

        R.id.progress_bar.isDisplayed()
        R.id.recycler_view.isNotDisplayed()
        R.id.error_views.isNotDisplayed()
    }
}