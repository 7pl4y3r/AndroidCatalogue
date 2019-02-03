package com.apps.a7pl4y3r.catalogue.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val colId = "id"
private const val colMark = "mark"
private const val colDate = "date"

class MarkDatabase(context: Context, private val tableName: String) : SQLiteOpenHelper(context, tableName, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $tableName ($colId INTEGER PRIMARY KEY AUTOINCREMENT, $colMark TEXT, $colDate TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $tableName")
        onCreate(db)
    }

    fun insertMark(mark: String, date: String): Boolean {

        val cv = ContentValues()
        cv.put(colMark, mark)
        cv.put(colDate, date)

        return this.writableDatabase.insert(tableName, null, cv) != -1L
    }

    fun updateMark(id: String, mark: String): Boolean {

        val cv = ContentValues()
        cv.put(colMark, mark)
        this.writableDatabase.update(tableName, cv, "$colId = ?", arrayOf(id))

        return true
    }

    fun getMarks(): Cursor = this.writableDatabase.rawQuery("select * from $tableName", null)

    fun deleteMark(id: String): Int? = this.writableDatabase.delete(tableName, "$colId = ?", arrayOf(id))

    fun getCount(): Int {

        val res = getMarks()
        var c = 0

        res.moveToFirst()
        do {
            c++
        } while (res.moveToNext())

        return c
    }

}