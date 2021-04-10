package com.example.mocknetwork.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mocknetwork.R
import com.example.mocknetwork.ui.LoginViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(Enclosed::class)
class LoginViewModelTest {
    abstract class Base {

        @get:Rule
        val rule = InstantTaskExecutorRule()

        lateinit var loginViewModel: LoginViewModel

        @Mock
        lateinit var application: Application

        @Mock
        lateinit var context: Context

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)
            Mockito.`when`(application.applicationContext).thenReturn(context)
            loginViewModel = LoginViewModel(application)
        }
    }

    @RunWith(Parameterized::class)
    class UserNameErrorCodeTest(private var userName: String, private var expectedResult: Int) : Base() {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): Collection<Array<Any>> {
                return listOf(arrayOf("", R.string.accountLogin_username_errorEmptyUnselected),
                        arrayOf("1222", R.string.accountLogin_username_errorFormatUnselected),
                        arrayOf("123456s", 0),
                        arrayOf("ssssssss", R.string.accountLogin_username_errorFormatUnselected))
            }
        }

        @Test
        fun shouldValidatePhoneNumber() {
            loginViewModel.validateLoginUsername(userName)
            Assertions.assertEquals(expectedResult, loginViewModel.userNameErrorMessageId)
        }
    }

    @RunWith(Parameterized::class)
    class PasswordErrorCodeTest(private var password: String, private var expectedResult: Int) : Base() {
        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data(): Collection<Array<Any>> {
                return listOf(arrayOf("123", R.string.accountLogin_password_errorEmptyUnselected),
                        arrayOf("", R.string.accountLogin_password_errorEmptyUnselected),
                        arrayOf("123456s", 0),
                        arrayOf("ssssssss", R.string.accountLogin_password_errorEmptyUnselected))
            }
        }

        @Test
        fun shouldValidatePhoneNumber() {
            loginViewModel.validatePassword(password)
            Assertions.assertEquals(expectedResult, loginViewModel.passwordErrorMessageId)
        }
    }
}