package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import com.apps.a7pl4y3r.catalogue.helpers.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setRecyclerView()

        fabMain.setOnClickListener {
            startActivity(Intent(this, AddDiscipline::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences(settingDisciplineWasAdded, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(valDisciplineWasAdded, false)) {

            sharedPref.edit().putBoolean(valDisciplineWasAdded, false).apply()
            setRecyclerView()
        }

    }

    private fun setToolbar() {

        setSupportActionBar(tooblarMain)
        tooblarMain.title = mainTitle
    }

    private fun setRecyclerView() {

        val adapter = RecyclerViewAdapter(getDisciplineList())

        recyclerViewMain.setHasFixedSize(true)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter

        setRecyclerViewItemClick(adapter)
    }

    private fun setRecyclerViewItemClick(adapter: RecyclerViewAdapter) {

        adapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {

            override fun onItemClick(discipline: Discipline, position: Int) {
               showToast(this@MainActivity, "Pressed card $position", false)
            }
        })

    }

    private fun getDisciplineList(): ArrayList<Discipline> {

        val items = ArrayList<Discipline>()
        val db = DisciplineDatabase(this)
        val res = db.getDisciplines()

        if (res.count == 0) {
            items.add(Discipline("-1", "Empty!?", "Why don't you add a discipline?"))

        } else {

            res.moveToFirst()
            do {
                items.add(Discipline(res.getString(0), res.getString(1), "0"))
            } while (res.moveToNext())
        }

        res.close()
        db.close()

        return items
    }

}
