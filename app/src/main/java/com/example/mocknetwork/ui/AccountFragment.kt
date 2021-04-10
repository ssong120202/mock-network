package com.example.mocknetwork.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mocknetwork.R
import com.example.mocknetwork.util.withArgs
import kotlinx.android.synthetic.main.account_fragment.*

class AccountFragment : Fragment() {

    private var userName: String = ""

    companion object {
        private const val KEY_USER_NAME = "userName"

        fun newInstance(userName: String) = AccountFragment().withArgs {
            putString(KEY_USER_NAME, userName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userName = it.getString(KEY_USER_NAME, "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        account_fragment_user_name_text_view.text = userName
    }
}