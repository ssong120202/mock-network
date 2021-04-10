package com.example.mocknetwork.model

import java.io.Serializable

data class UserInfoResponse(var successful: Boolean = false, var userName: String = "") : Serializable

data class UserInfo(var userName: String = "", var password: String = "") : Serializable