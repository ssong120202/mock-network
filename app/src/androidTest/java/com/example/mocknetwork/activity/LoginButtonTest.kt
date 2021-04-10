package com.example.mocknetwork.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.mocknetwork.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun loginButtonTest() {
        val frameLayout = onView(allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed()))
        frameLayout.check(doesNotExist())

        val frameLayout2 = onView(allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed()))
        frameLayout2.check(doesNotExist())

        val textInputEditText = onView(allOf(withId(R.id.account_login_user_name_edit_text), childAtPosition(childAtPosition(withId(R.id.account_login_user_name_layout), 0), 0)))
        textInputEditText.perform(scrollTo(), replaceText("shauna124"), closeSoftKeyboard())

        val textInputEditText2 = onView(allOf(withId(R.id.account_login_user_name_edit_text), withText("shauna124"), childAtPosition(childAtPosition(withId(R.id.account_login_user_name_layout), 0), 0)))
        textInputEditText2.perform(pressImeActionButton())

        val textInputEditText3 = onView(allOf(withId(R.id.account_login_password_edit_text), childAtPosition(childAtPosition(withId(R.id.account_login_password_layout), 0), 0)))
        textInputEditText3.perform(scrollTo(), replaceText("123456ss"), closeSoftKeyboard())

        val textInputEditText4 = onView(allOf(withId(R.id.account_login_password_edit_text), withText("123456ss"), childAtPosition(childAtPosition(withId(R.id.account_login_password_layout), 0), 0)))
        textInputEditText4.perform(pressImeActionButton())

        val appCompatButton = onView(allOf(withId(R.id.account_login_login_button), withText("Login"), childAtPosition(allOf(withId(R.id.account_login_login_button_container), childAtPosition(withId(R.id.account_login_credentials_layout), 5)), 0)))
        appCompatButton.perform(scrollTo(), click())
    }

    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(position)
            }
        }
    }
}
