package com.example.pushrating.data

import android.annotation.SuppressLint
import android.content.Context

internal class IAPRepo(private val context: Context) {

    private val pref =
        context.applicationContext.getSharedPreferences("push_rate_settings", Context.MODE_PRIVATE)

    companion object {

        @SuppressLint("StaticFieldLeak")
        var instance: IAPRepo? = null
        fun getInstance(context: Context): IAPRepo {
            if (instance == null) {
                instance = IAPRepo(context)
            }
            return instance!!
        }

        // The number of times open app to show
        const val IAP_CALL_SHOW_COUNT_KEY = "iap_call_show_count"

        // Enable show or not
        const val IAP_ENABLE_SHOW_KEY = "iap_enable_show"

        // The interval for start app first time to show push rate (sec)
        const val IAP_TIME_THRESHOLD_KEY = "iap_time_threshold"

        // The time mile stone to calculate base on IAP_TIME_THRESHOLD_KEY (sec)
        const val IAP_TIME_MILESTONE_KEY = "iap_time_milestone"
    }

    var callShowCount: Int
        get() {
            return pref.getInt(IAP_CALL_SHOW_COUNT_KEY, 1)
        }
        set(value) {
            pref.edit().putInt(IAP_CALL_SHOW_COUNT_KEY, value)?.apply()
        }

    var timeThreshold: Long
        get() {
            return pref.getLong(IAP_TIME_THRESHOLD_KEY, 30)
        }
        set(value) {
            pref.edit().putLong(IAP_TIME_THRESHOLD_KEY, value).apply()
        }

    var lastTimeMileStone: Long
        get() {
            return pref.getLong(IAP_TIME_MILESTONE_KEY, -1)
        }
        set(value) {
            pref.edit().putLong(IAP_TIME_MILESTONE_KEY, value).apply()
        }

    var enableShow: Boolean
        get() {
            return pref.getBoolean(IAP_ENABLE_SHOW_KEY, true)
        }
        set(value) {
            pref.edit().putBoolean(IAP_ENABLE_SHOW_KEY, value)?.apply()
        }

}