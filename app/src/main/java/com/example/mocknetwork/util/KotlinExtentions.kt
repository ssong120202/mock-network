package com.example.mocknetwork.util

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * simple method to eliminate some boilerplate code when save vars to Bundle and attach it to fragment
 */
inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}