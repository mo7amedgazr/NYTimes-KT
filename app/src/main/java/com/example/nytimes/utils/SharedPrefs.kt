package com.example.nytimes.utils

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefs(context: Context) {
    companion object {
        private const val PREF = "MyAppPref"
        private const val PREF_LANGUAGE = "user_language"
        private const val SDK_LAUNCHED = "sdk_launched"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)




    fun saveLanguage(language: String) {
        put(PREF_LANGUAGE, language)
    }

    fun getLanguage(): String {
        return get(PREF_LANGUAGE, String::class.java)
    }

    fun saveSdkLaunch() {
        put(SDK_LAUNCHED, true)
    }

    fun isSdkLaunchedBefore(): Boolean {
        return get(SDK_LAUNCHED, Boolean::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

}