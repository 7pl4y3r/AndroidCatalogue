package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import kotlinx.android.synthetic.main.activity_add_discipline.*

class EditDiscipline : AppCompatActivity() {

    private val discipline: Discipline?

    init {
        val main = MainActivity()
        discipline = main.getDiscipline()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discipline)
        setInitialData()

        addDisciplineCancel.setOnClickListener {
            finish()
        }

        addDisciplineSave.setOnClickListener {

            if (!addDisciplineEditText.text.isEmpty() && discipline != null) {

                val db = DisciplineDatabase(this)
                db.updateDiscipline(discipline.id, addDisciplineEditText.text.toString())
                showToast(this, "Discipline edited", false)

                getSharedPreferences(settingDisciplineChange, Context.MODE_PRIVATE).edit()
                    .putBoolean(valDisciplineChange, true).apply()

                db.close()
                finish()

            } else {
                showToast(this, "Error!", true)
            }

        }

    }


    private fun setInitialData() {

        addDisciplineTitle.text = "Edit discipline"
        addDisciplineTitle.text = if (discipline != null) discipline.title else "Got no discipline!"

    }

}