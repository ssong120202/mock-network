package com.example.mocknetwork.service

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mocknetwork.model.UserInfo
import com.example.mocknetwork.model.UserInfoResponse

class MockLoginRepository private constructor(private val application: Application) {
    private val url = "https://fireflysoftware.ca/"
    var userInfoLiveData = MutableLiveData<UserInfoResponse>()

    companion object {
        @Volatile
        private var INSTANCE: MockLoginRepository? = null
        fun getInstance(application: Application): MockLoginRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MockLoginRepository(application).also { INSTANCE = it }
            }
        }
    }

    fun fetchUserInfo(context: Context, userInfo: UserInfo) {
        val stringRequest = StringRequest(Request.Method.GET, url, {
            val newUserResponse = getMockUserInfo(userInfo)
            userInfoLiveData.postValue(newUserResponse)
        }) { volleyError ->
            Toast.makeText(application.applicationContext, "error,$volleyError", Toast.LENGTH_LONG).show()
        }
        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(stringRequest)
    }

    private fun getMockUserInfo(userInfo: UserInfo): UserInfoResponse {
        return UserInfoResponse(true, userInfo.userName)
    }
}