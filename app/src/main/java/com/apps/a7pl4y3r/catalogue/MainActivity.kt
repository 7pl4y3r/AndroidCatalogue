package com.apps.a7pl4y3r.catalogue

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.view.MenuItem
import com.apps.a7pl4y3r.catalogue.helpers.Discipline
import com.apps.a7pl4y3r.catalogue.helpers.DisciplineDatabase
import com.apps.a7pl4y3r.catalogue.helpers.MarkDatabase
import com.apps.a7pl4y3r.catalogue.helpers.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast

import android.support.v7.widget.RecyclerView




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var adapter: RecyclerViewAdapter

    private val items = ArrayList<Discipline>()
    private val itemsToDelete = ArrayList<String>()

    private var wantsToEdit = false
    private var wantsToDelete = false

    private var hasDiscipline = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        setRecyclerView()
        setMainDrawer()

        fabMain.setOnClickListener { startActivityForResult(Intent(this, AddDiscipline::class.java), REQUEST_ADD_SUBJECT) }

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

        if (hasDiscipline) {

            when (item.itemId) {

                R.id.menu_item_EditDiscipline -> {

                    if (wantsToDelete)
                        wantsToDelete = false

                    if (wantsToEdit) {

                        wantsToEdit = false
                        showToast(this, "Done editing", false)

                    } else {

                        wantsToEdit = true
                        showToast(this, "Press the item you want to edit", true)

                    }


                }

                R.id.menu_item_DeleteDiscipline -> {

                    if (wantsToEdit)
                        wantsToEdit = false

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
                        showToast(
                            this,
                            "Press the items you want to delete and then press the button you just pressed again",
                            true
                        )

                    }

                }

            }

        }

        drawerMain.closeDrawer(Gravity.START)

        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                REQUEST_ADD_SUBJECT -> {

                    val db = DisciplineDatabase(this)
                    val pref = getSharedPreferences(settingDbNumber, Context.MODE_PRIVATE)
                    val dbNumber = pref.getInt(valDbNumber, 0)

                    db.insertDiscipline("$codeName$dbNumber", data!!.getStringExtra(EXTRA_TITLE))
                    pref.edit().putInt(valDbNumber, dbNumber + 1).apply()

                    items.add(Discipline("0", data.getStringExtra(EXTRA_TITLE), "0"))
                    adapter.notifyDataSetChanged()

                }

            }

        } else Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()

    }

    private fun setToolbar() {

        setSupportActionBar(toolbarMain)
        toolbarMain.title = mainTitle
    }

    private fun setRecyclerView() {

        setDisciplineList()
        adapter = RecyclerViewAdapter(this, items)

        recyclerViewMain.setHasFixedSize(true)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        recyclerViewMain.adapter = adapter

        setRecyclerViewItemClick(adapter)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val db = DisciplineDatabase(this@MainActivity)
                val item = adapter.getItemAt(viewHolder.adapterPosition)

                items.remove(item)
                db.deleteDiscipline(item.id)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                Toast.makeText(this@MainActivity, "Deleted!", Toast.LENGTH_SHORT).show()

            }

        }).attachToRecyclerView(recyclerViewMain)

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

                } else if (hasDiscipline) {

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
            hasDiscipline = false

        } else {

            hasDiscipline = true

            var markDb: MarkDatabase

            res.moveToFirst()
            do {

                markDb = MarkDatabase(this, res.getString(1))
                items.add(Discipline(res.getString(0), res.getString(2), markDb.getCountInString()))
                markDb.close()

            } while (res.moveToNext())
        }

        res.close()
        db.close()

    }

}