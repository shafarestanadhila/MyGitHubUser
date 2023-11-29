package com.example.mygithubuser.ui.activity

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.mygithubuser.R
import com.example.mygithubuser.ui.adapter.UserAdapter
import com.example.mygithubuser.utils.EspressoIdlingResource
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun atestComponentShowCorrectly() {
        Thread.sleep(2000)
        onView(withId(R.id.topAppBar)).check(matches(isDisplayed()))
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_user)).perform(
            actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.user_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_favorite)).perform(click())
        pressBack()

        Thread.sleep(2000)
        onView(withId(R.id.menu2)).perform(click())
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).perform(
            actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, click())
        )

        Thread.sleep(2000)
        onView(withId(R.id.user_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_favorite)).perform(click())
        pressBack()
    }

    @Test
    fun btestSearchUserFound() {
        onView(withId(R.id.searchView)).check(matches(isDisplayed()))
        onView(withId(R.id.searchView)).perform(click())

        onView(
            withId(androidx.appcompat.R.id.search_src_text)
        ).perform(
            clearText(), typeText("shafarestanadhila")
        ).perform(pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(2000)

        onView(withId(R.id.rv_user))
            .perform(
                actionOnItemAtPosition<UserAdapter.UserViewHolder>(
                    0,
                    click()
                )
            )

        Thread.sleep(2000)
        onView(withId(R.id.user_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
        onView(withId(R.id.toggle_favorite)).check(matches(isDisplayed()))
    }
}

