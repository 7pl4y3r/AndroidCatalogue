package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import kotlinx.android.synthetic.main.activity_add_discipline.*

class AddDiscipline : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discipline)

        addDisciplineCancel.setOnClickListener {
            finish()
        }

        addDisciplineSave.setOnClickListener {

            if (!addDisciplineEditText.text.isEmpty()) {

                val db = DisciplineDatabase(this)
                if (db.insertDiscipline(addDisciplineEditText.text.toString())) {

                    showToast(this, "Success!", false)
                    getSharedPreferences(settingDisciplineWasAdded, Context.MODE_PRIVATE).edit()
                        .putBoolean(valDisciplineWasAdded, true).apply()

                } else {
                    showToast(this, "Database insertion failed!", true)
                }

                db.close()

            } else {
                showToast(this, "The discipline needs a name!", false)
            }

            finish()
        }

    }
}
