package com.example.pushrating.data

import android.content.Context

class SettingRepo (private val context: Context) {

    private val pref = context.applicationContext.getSharedPreferences("push_rate_settings", Context.MODE_PRIVATE)

    companion object {
        const val CALL_SHOW_COUNT_KEY = "call_show_count"
        const val ENABLE_SHOW_KEY = "enable_show"
    }

    var callShowCount: Int
    get() {
        return pref.getInt(CALL_SHOW_COUNT_KEY, 1)
    }
    set(value) {
        pref.edit().putInt(CALL_SHOW_COUNT_KEY, value)?.apply()
    }

    var enableShow: Boolean
        get() {
            return pref.getBoolean(ENABLE_SHOW_KEY, true)
        }
        set(value) {
            pref.edit().putBoolean(ENABLE_SHOW_KEY, value)?.apply()
        }
}