package com.example.mocknetwork.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mocknetwork.R
import com.example.mocknetwork.ui.LoginFragment
import com.example.mocknetwork.util.NavigationUtil

class MainActivity : AppCompatActivity() {
    private val navigation: NavigationUtil = NavigationUtil.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager.findFragmentById(R.id.modal_container)
        navigation.setFragmentManager(supportFragmentManager)
        NavigationUtil.getInstance().pushOrPopFragment(LoginFragment.newInstance(), 0, true)
    }
}