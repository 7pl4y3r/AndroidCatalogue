package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.widget.Toast
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import java.util.*

const val mainTitle = "Your notes"

//SharedPreferences
const val settingDisciplineChange = "SettingDisciplineChange"
const val valDisciplineChange = "ValDisciplineChange"

fun showToast(context: Context, message: String, isLong: Boolean) {
    Toast.makeText(context, message, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun clearArrayListOfString(arrayList: ArrayList<String>) {

    while (arrayList.size > 0)
        arrayList.removeAt(0)

}

fun clearArrayListOfDiscipline(arrayList: ArrayList<Discipline>) {

    while (arrayList.size > 0)
        arrayList.removeAt(0)

}

fun printStringArrayList(context: Context, arrayList: ArrayList<String>) {

    for (element in arrayList) {
        showToast(context, element, false)
    }

}