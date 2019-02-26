package com.apps.a7pl4y3r.catalogue

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import kotlinx.android.synthetic.main.activity_add_discipline.*

class AddDiscipline : AppCompatActivity() {


    private lateinit var db: DisciplineDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discipline)

        addDisciplineCancel.setOnClickListener {
            finish()
        }

        addDisciplineSave.setOnClickListener {

            if (!addDisciplineEditText.text.isEmpty() && nameIsGood(addDisciplineEditText.text.toString())) {


                    val data = Intent()
                    data.putExtra(EXTRA_TITLE, addDisciplineEditText.text.toString())
                    setResult(Activity.RESULT_OK, data)

                    showToast(this, "Success!", false)


            } else {
                setResult(Activity.RESULT_CANCELED)
                showToast(this, "The discipline name is unacceptable!", false)
            }

            finish()
        }

    }

    private fun nameIsGood(name: String): Boolean {


        if (name.contains(' ') || name.contains('-'))
            return false


        db = DisciplineDatabase(this)
        val res = db.getDisciplines()

        res.moveToFirst()

        if (res.count > 0)
        do {

            if (name == res.getString(1))
                return false

        } while (res.moveToNext())


        return true
    }

}
