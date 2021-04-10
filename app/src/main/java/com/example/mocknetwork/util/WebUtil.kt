package com.example.mocknetwork.util

import android.R
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object WebUtil {
    fun openUrlInApp(context: Context?, url: String?) {
        if (context == null || url == null || url.trim { it <= ' ' }.isEmpty()) {
            return
        }
        val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(context.getColor(R.color.black))
        val customTabsIntent: CustomTabsIntent = builder.build()
        try {
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (e: ActivityNotFoundException) {
            openUrlWithDefaultBrowser(context, url)
        }
    }

    private fun openUrlWithDefaultBrowser(context: Context?, url: String?) {
        if (context == null || url == null || url.isEmpty()) {
            return
        }
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}