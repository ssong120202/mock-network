package com.example.mocknetwork.activity


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class UserNameClearButtonTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun userNameClearButtonTest() {
        val frameLayout = onView(allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed()))
        frameLayout.check(doesNotExist())

        val frameLayout2 = onView(allOf(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java), isDisplayed()))
        frameLayout2.check(doesNotExist())

        val textInputEditText = onView(allOf(withId(R.id.account_login_user_name_edit_text), childAtPosition(childAtPosition(withId(R.id.account_login_user_name_layout), 0), 0)))
        textInputEditText.perform(scrollTo(), replaceText("12345sss"), closeSoftKeyboard())

        val appCompatImageButton = onView(allOf(withId(R.id.account_login_clear_button), childAtPosition(allOf(withId(R.id.account_login_credentials_layout), childAtPosition(withId(R.id.account_login_credentials_scroll_view), 0)), 1)))
        appCompatImageButton.perform(scrollTo(), click())

        val textInputEditText2 = onView(allOf(withId(R.id.account_login_user_name_edit_text), childAtPosition(childAtPosition(withId(R.id.account_login_user_name_layout), 0), 0)))
        textInputEditText2.perform(scrollTo(), click())

        val textInputEditText3 = onView(allOf(withId(R.id.account_login_user_name_edit_text), childAtPosition(childAtPosition(withId(R.id.account_login_user_name_layout), 0), 0)))
        textInputEditText3.perform(scrollTo(), replaceText("12sxd"), closeSoftKeyboard())

        val appCompatImageButton2 = onView(allOf(withId(R.id.account_login_clear_button), childAtPosition(allOf(withId(R.id.account_login_credentials_layout), childAtPosition(withId(R.id.account_login_credentials_scroll_view), 0)), 1)))
        appCompatImageButton2.perform(scrollTo(), click())
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
