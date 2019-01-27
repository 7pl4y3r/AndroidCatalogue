package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.widget.Toast

const val mainTitle = "Your notes"

//SharedPreferences
const val settingDisciplineWasAdded = "SettingDisciplineWasAdded"
const val valDisciplineWasAdded = "ValDisciplineWasAdded"

fun showToast(context: Context, message: String, isLong: Boolean) {
    Toast.makeText(context, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}