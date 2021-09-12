package com.example.pushrating.data

import android.annotation.SuppressLint
import android.content.Context

class SettingRepo(private val context: Context) {

    private val pref =
        context.applicationContext.getSharedPreferences("push_rate_settings", Context.MODE_PRIVATE)

    companion object {

        @SuppressLint("StaticFieldLeak")
        var instance: SettingRepo? = null
        fun getInstance(context: Context): SettingRepo {
            if (instance == null) {
                instance = SettingRepo(context)
            }
            return instance!!
        }

        // The number of times open app to show
        const val CALL_SHOW_COUNT_KEY = "call_show_count"

        // Enable show or not
        const val ENABLE_SHOW_KEY = "enable_show"

        // The interval for start app first time to show push rate (sec)
        const val TIME_THRESHOLD_KEY = "time_threshold"

        // The time mile stone to calculate base on TIME_THRESHOLD_KEY (sec)
        const val TIME_MILESTONE_KEY = "time_milestone"
    }

    var callShowCount: Int
        get() {
            return pref.getInt(CALL_SHOW_COUNT_KEY, 1)
        }
        set(value) {
            pref.edit().putInt(CALL_SHOW_COUNT_KEY, value)?.apply()
        }

    var timeThreshold: Long
        get() {
            return pref.getLong(TIME_THRESHOLD_KEY, 30)
        }
        set(value) {
            pref.edit().putLong(TIME_THRESHOLD_KEY, value).apply()
        }

    var lastTimeMileStone: Long
        get() {
            return pref.getLong(TIME_MILESTONE_KEY, -1)
        }
        set(value) {
            pref.edit().putLong(TIME_MILESTONE_KEY, value).apply()
        }

    var enableShow: Boolean
        get() {
            return pref.getBoolean(ENABLE_SHOW_KEY, true)
        }
        set(value) {
            pref.edit().putBoolean(ENABLE_SHOW_KEY, value)?.apply()
        }
}