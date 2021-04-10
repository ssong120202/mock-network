package com.example.mocknetwork.activity


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.mocknetwork.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UiElementTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun uiElementTest() {
        val textView = onView(allOf(withId(R.id.account_login_title_header_text_view), withText("Welcome to FireFly!"), withParent(allOf(withId(R.id.account_login_title_layout), withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java)))), isDisplayed()))
        textView.check(matches(withText("Welcome to FireFly!")))

        val textView2 = onView(allOf(withId(R.id.account_login_create_account_text_view), withText("Create User ?"), withParent(allOf(withId(R.id.account_login_create_account_forgot_password_layout), withParent(withId(R.id.account_login_credentials_layout)))), isDisplayed()))
        textView2.check(matches(withText("Create User ?")))

        val textView3 = onView(allOf(withId(R.id.account_login_forgot_password_view), withText("Forgot password ?"), withParent(allOf(withId(R.id.account_login_create_account_forgot_password_layout), withParent(withId(R.id.account_login_credentials_layout)))), isDisplayed()))
        textView3.check(matches(withText("Forgot password ?")))
    }
}
