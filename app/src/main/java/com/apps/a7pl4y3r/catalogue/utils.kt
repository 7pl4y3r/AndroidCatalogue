package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.widget.Toast
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import com.apps.a7pl4y3r.catalogue.helpers.Mark
import java.util.*
import kotlin.collections.ArrayList

const val mainTitle = "Your notes"

//SharedPreferences
const val settingDisciplineChange = "SettingDisciplineChange"
const val valDisciplineChange = "ValDisciplineChange"

const val editDisciplineIntentKey = "editDisciplineTitle"
const val editMarkIntentKey = "editMarkTitle"

//Requests
const val REQUEST_ADD_SUBJECT = 1

// Extras discipline
const val EXTRA_TITLE = "com.apps.a7pl4y3r.catalogue.EXTRA_TITLE"

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

fun clearArrayListOfMark(arrayList: ArrayList<Mark>) {

    while (arrayList.size > 0)
        arrayList.removeAt(0)

}

fun printStringArrayList(context: Context, arrayList: ArrayList<String>) {

    for (element in arrayList) {
        showToast(context, element, false)
    }

}

fun getCurrentDateString(): String {

    val calendar = Calendar.getInstance()
    val month = getMonthString(calendar.get(Calendar.MONTH))
    val day = "${calendar.get(Calendar.DAY_OF_MONTH)}"
    val year = "${calendar.get(Calendar.YEAR)}"

    return "$month $day $year"
}

private fun getMonthString(id: Int): String {

    when (id) {

        0 -> return "Jan"
        1 -> return "Feb"
        2 -> return "March"
        3 -> return "April"
        4 -> return "May"
        5 -> return "June"
        6 -> return "July"
        7 -> return "Aug"
        8 -> return "Sep"
        9 -> return "Oct"
        10 -> return "Nov"
        11 -> return "Dec"

    }

    return "LOLOL"
}