package com.tuwaiq.movieapp.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class SettingUtil (private val context: Context){
    fun saveLang(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources?.updateConfiguration(config,context.resources?.displayMetrics)
    }
}