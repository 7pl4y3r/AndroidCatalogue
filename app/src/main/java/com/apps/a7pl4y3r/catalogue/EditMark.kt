package com.apps.a7pl4y3r.catalogue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apps.a7pl4y3r.catalogue.helpers.Mark
import com.apps.a7pl4y3r.catalogue.helpers.MarkDatabase
import kotlinx.android.synthetic.main.activity_edit_mark.*

class EditMark : AppCompatActivity() {

    private lateinit var db: MarkDatabase
    private var mark: Mark? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_mark)
        setInitialData()

        editMarkCancel.setOnClickListener { finish() }

        editMarkSave.setOnClickListener {

            if (!editMarkEditText.text.isEmpty()) {

                if (intent.getStringExtra(editMarkIntentKey) == null)
                    db.insertMark(editMarkEditText.text.toString(), editMarkDate.text.toString())
                else
                    db.updateMark(mark!!.markId, mark!!.markTitle)

                showToast(this, "Mark edited", false)
                finish()

            } else {

                showToast(this, "No mark inserted", false)

            }

        }

    }

    private fun setInitialData() {

        db = MarkDatabase(this, intent.getStringExtra(editDisciplineIntentKey))
        mark = getMarkById()

        editMarkTitle.text = if (intent.getStringExtra(editMarkIntentKey) == null) "Add mark" else "Edit mark"
        editMarkEditText.setText(mark?.markTitle ?: "")
        editMarkDate.text = mark?.markSubtitle ?: getCurrentDateString()

    }

    private fun getMarkById(): Mark? {

        val res = db.getMarks()

        if (res.count == 0)
            return null

        res.moveToFirst()
        do {

            if (res.getString(0) == intent.getStringExtra(editMarkIntentKey))
                return Mark(res.getString(0), res.getString(1), res.getString(2))

        } while (res.moveToNext())

        return null
    }

}