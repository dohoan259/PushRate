package com.example.pushrating.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent

fun Context.openAppInStore() {
    val uri = android.net.Uri.parse("market://details?id=$packageName")
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    // To count with Play market backstack, After pressing back button,
    // to taken back to our application, we need to add following flags to intent.
    goToMarket.addFlags(
        android.content.Intent.FLAG_ACTIVITY_NO_HISTORY or
                android.content.Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}