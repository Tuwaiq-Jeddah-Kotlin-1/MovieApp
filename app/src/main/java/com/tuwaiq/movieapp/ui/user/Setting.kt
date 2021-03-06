package com.tuwaiq.movieapp.ui.user

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.tuwaiq.movieapp.R
import com.tuwaiq.movieapp.utils.SettingUtil

class Setting : Fragment(R.layout.fragment_setting) {

    private lateinit var languageTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var setting: SettingUtil

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        languageTextView = view.findViewById(R.id.language)
        languageTextView.setOnClickListener {
            showChangeLanguage()
        }
        setting = SettingUtil(requireContext())
    }

    //save and set language
    private fun saveLang(lang: String) {
        setting.saveLang(lang)
        sharedPreferences =
            this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("MY_LANG", lang)
        editor.apply()
    }

    //show change language
    private fun showChangeLanguage() {
        val languageList = arrayOf("English", "العربية")
        val mBuilder = AlertDialog.Builder(this.context)
        var current = 0
        sharedPreferences =
            this.requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString("MY_LANG", "")!!
        if (lang == "en") {
            current = 0
        } else if (lang == "ar") {
            current = 1
        }
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(languageList, current) { dialog, which ->
            when {
                current!=which -> when (which) {
                    0 -> {
                        saveLang("en")
                    }
                    1 -> {
                        saveLang("ar")
                    }
                }
            }
            recreate(context as Activity)
            dialog.dismiss()
        }
        mBuilder.create().show()
    }
}