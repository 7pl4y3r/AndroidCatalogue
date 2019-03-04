package com.apps.a7pl4y3r.catalogue.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val databaseName = "disciplineList.db"
private const val tableName = "disciplineListTable"
private const val colId = "id"
private const val colCodeName = "code_name"
private const val colName = "name"

class DisciplineDatabase(context: Context) : SQLiteOpenHelper(context, databaseName, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $tableName ($colId INTEGER PRIMARY KEY AUTOINCREMENT, $colCodeName TEXT, $colName TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $tableName")
        onCreate(db)
    }

    fun insertDiscipline(codeName: String, name: String): Boolean {

        val cv = ContentValues()
        cv.put(colCodeName, codeName)
        cv.put(colName, name)

        return this.writableDatabase.insert(tableName, null, cv) != -1L
    }

    fun updateDiscipline(id: String, name: String): Boolean {

        val cv = ContentValues()
        cv.put(colName, name)
        this.writableDatabase.update(tableName, cv, "$colId = ?", arrayOf(id))

        return true
    }

    fun getDisciplines(): Cursor = this.writableDatabase.rawQuery("select * from $tableName", null)

    fun deleteDiscipline(id: String): Int? = this.writableDatabase.delete(tableName, "$colId = ?", arrayOf(id))

}