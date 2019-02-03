package com.apps.a7pl4y3r.catalogue

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import com.apps.a7pl4y3r.catalogue.helpers.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val items = ArrayList<Discipline>()
    private val itemsToDelete = ArrayList<String>()

    private var wantsToEdit = false
    private var wantsToDelete = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setRecyclerView()
        setMainDrawer()

        fabMain.setOnClickListener { startActivity(Intent(this, AddDiscipline::class.java)) }

    }

    override fun onResume() {
        super.onResume()

        val sharedPref = getSharedPreferences(settingDisciplineChange, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(valDisciplineChange, false)) {

            clearArrayListOfDiscipline(items)
            sharedPref.edit().putBoolean(valDisciplineChange, false).apply()
            setRecyclerView()

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_item_EditDiscipline -> { wantsToEdit = true }

            R.id.menu_item_DeleteDiscipline -> {

                if (wantsToDelete) {

                    wantsToDelete = false

                    val db = DisciplineDatabase(this)
                    var res = db.getDisciplines()


                    for (element in itemsToDelete) {

                        res = db.getDisciplines()
                        res.moveToFirst()
                        while (element != res.getString(1))
                            res.moveToNext()


                        db.deleteDiscipline(res.getString(0))

                    }

                    clearArrayListOfString(itemsToDelete)
                    clearArrayListOfDiscipline(items)

                    db.close()
                    res.close()

                    showToast(this, "Items were deleted!", false)
                    setRecyclerView()

                } else {

                    wantsToDelete = true
                    showToast(this, "Press the items you want to delete and then press the button you just pressed again", true)

                }

            }

        }

        return true
    }

    private fun setToolbar() {

        setSupportActionBar(toolbarMain)
        toolbarMain.title = mainTitle
    }

    private fun setRecyclerView() {

        setDisciplineList()
        val adapter = RecyclerViewAdapter(this, items)

        recyclerViewMain.setHasFixedSize(true)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter

        setRecyclerViewItemClick(adapter)
    }

    private fun setRecyclerViewItemClick(adapter: RecyclerViewAdapter) {

        adapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {

            override fun onItemClick(card: CardView, items: ArrayList<Discipline>, position: Int) {

                if (wantsToDelete) {

                    if (card.cardBackgroundColor == ContextCompat.getColorStateList(this@MainActivity, R.color.cardColorDefault)) {

                        //Add to deletion
                        itemsToDelete.add(items[position].title)
                        card.setCardBackgroundColor(ContextCompat.getColorStateList(this@MainActivity, R.color.cardColorRed))

                    } else {

                        //Remove from deletion
                        itemsToDelete.remove(items[position].title)
                        card.setCardBackgroundColor(ContextCompat.getColorStateList(this@MainActivity, R.color.cardColorDefault))

                    }

                } else if (wantsToEdit) {

                    wantsToEdit = false
                    startActivity(Intent(this@MainActivity, EditDiscipline::class.java).putExtra(editDisciplineIntentKey, items[position].title))

                } else {

                    startActivity(Intent(this@MainActivity, ViewMarks::class.java).putExtra(editDisciplineIntentKey, items[position].title))

                }
            }
        })

    }

    private fun setMainDrawer() {

        val toggle = ActionBarDrawerToggle(this, drawerMain, toolbarMain, R.string.openDrawer, R.string.closeDrawer)
        drawerMain.addDrawerListener(toggle)
        toggle.syncState()
        navigationViewMain.setNavigationItemSelectedListener(this)

    }

    private fun setDisciplineList() {

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

    }

}