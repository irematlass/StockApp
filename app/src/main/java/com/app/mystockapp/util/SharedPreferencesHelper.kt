package com.app.mystockapp.util

import android.content.Context

class SharedPreferencesHelper(private val context: Context) {

    fun setSharedPreference(key: String?, value: String?) {
        val sharedPref = context.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.putString(key, value)
        edit.commit()
    }

    fun getSharedPreference(key: String?, defaultValue: String?): String? {
        return context.getSharedPreferences("PREF", Context.MODE_PRIVATE)
            .getString(key, defaultValue)
    }
}