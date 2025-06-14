package com.example.myapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.others.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val androidActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun Given() {
        onView(withId(R.id.editTextUsername)).perform(typeText("user"))
        onView(withId(R.id.editTextPassword)).perform(typeText("12345"))
        onView(withId(R.id.loginButton)).perform(click())
        onView(withText("Invalid Username/Password")).check(matches(isDisplayed()))
    }
}