package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase

import kotlinx.android.synthetic.main.activity_add_discipline.*

class EditDiscipline : AppCompatActivity() {

    private lateinit var db: DisciplineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discipline)
        setInitialData()

        addDisciplineCancel.setOnClickListener { finish() }

        addDisciplineSave.setOnClickListener {

            if (!addDisciplineEditText.text.isEmpty()) {

                db.updateDiscipline(getDisciplineId(), addDisciplineEditText.text.toString())
                showToast(this, "Updated discipline name", false)

                getSharedPreferences(settingDisciplineChange, Context.MODE_PRIVATE).edit()
                    .putBoolean(valDisciplineChange, true).apply()

                finish()

            }

        }

    }


    private fun setInitialData() {

        db = DisciplineDatabase(this)
        addDisciplineTitle.text = "Edit task"
        addDisciplineEditText.setText(intent.getStringExtra(editDisciplineIntentKey))

    }

    private fun getDisciplineId(): String {

        val res = db.getDisciplines()

        res.moveToFirst()
        do {

            if (res.getString(1) == intent.getStringExtra(editDisciplineIntentKey))
                return res.getString(0)

        } while (res.moveToNext())

        return "LOLOL"
    }

}