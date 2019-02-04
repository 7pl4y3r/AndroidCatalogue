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
import android.view.Gravity
import android.view.MenuItem
import com.apps.a7pl4y3r.catalogue.helpers.Mark
import com.apps.a7pl4y3r.catalogue.helpers.MarkDatabase
import com.apps.a7pl4y3r.catalogue.helpers.RecyclerViewAdapterMarks
import kotlinx.android.synthetic.main.activity_view_marks.*
import kotlin.collections.ArrayList

class ViewMarks : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val items = ArrayList<Mark>()
    private val itemsToDelete = ArrayList<String>()

    private var wantsToDelete = false
    private var wantsToEdit = false
    private var hasMarks = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_marks)
        setToolbar()
        setDrawer()
        setRecyclerView()

        viewMarksFab.setOnClickListener {
            startActivity(Intent(this, EditMark::class.java)
                .putExtra(editDisciplineIntentKey, intent.getStringExtra(editDisciplineIntentKey)))
        }

    }

    override fun onResume() {
        super.onResume()

        val pref = getSharedPreferences(settingDisciplineChange, Context.MODE_PRIVATE)
        if (pref.getBoolean(valDisciplineChange, false)) {

            clearArrayListOfMark(items)
            pref.edit().putBoolean(valDisciplineChange, false).apply()
            setRecyclerView()

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (hasMarks) {

            when (item.itemId) {

                R.id.menu_item_EditDiscipline -> {

                    if (wantsToDelete)
                        wantsToDelete = false

                    if (wantsToEdit) {

                        wantsToEdit = false
                        showToast(this, "Done editing", false)

                    } else {

                        wantsToEdit = true
                        showToast(this, "Press the item you want to edit", false)

                    }

                }

                R.id.menu_item_DeleteDiscipline -> {

                    if (wantsToEdit)
                        wantsToEdit = false

                    if (wantsToDelete) {

                        wantsToDelete = false

                        val db = MarkDatabase(this, intent.getStringExtra(editDisciplineIntentKey))
                        var res = db.getMarks()

                        for (element in itemsToDelete) {

                            res = db.getMarks()
                            res.moveToFirst()
                            while (element != res.getString(1))
                                res.moveToNext()

                            db.deleteMark(res.getString(0))

                        }

                        clearArrayListOfMark(items)
                        clearArrayListOfString(itemsToDelete)

                        db.close()
                        res.close()

                        showToast(this, "Marks deleted", false)
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

        viewMarksDrawer.closeDrawer(Gravity.START)

        return true
    }

    private fun setToolbar() {

        setSupportActionBar(viewMarksToolbar)
        viewMarksToolbar.title = intent.getStringExtra(editDisciplineIntentKey)

    }

    private fun setDrawer() {

        val toggle = ActionBarDrawerToggle(this, viewMarksDrawer, viewMarksToolbar, R.string.openDrawer, R.string.closeDrawer)
        toggle.syncState()
        viewMarksNavigationDrawer.setNavigationItemSelectedListener(this)

    }

    private fun setRecyclerView() {

        setItems()
        val adapter = RecyclerViewAdapterMarks(this, items)

        viewMarksRecyclerView.setHasFixedSize(true)
        viewMarksRecyclerView.layoutManager = LinearLayoutManager(this)
        viewMarksRecyclerView.adapter = adapter

        setRecyclerViewItemClick(adapter)

    }

    private fun setItems() {

        val db = MarkDatabase(this, intent.getStringExtra(editDisciplineIntentKey))
        val res = db.getMarks()

        if (res.count == 0) {
            items.add(Mark("-1", "No marks yet", getCurrentDateString()))
            hasMarks = false

        } else {

            hasMarks = true
            res.moveToFirst()
            do {

                items.add(Mark(res.getString(0), res.getString(1), res.getString(2)))

            } while (res.moveToNext())

        }

    }

    private fun setRecyclerViewItemClick(adapter: RecyclerViewAdapterMarks) {

        adapter.setOnItemClickListener(object : RecyclerViewAdapterMarks.OnItemClickListener {

            override fun onItemClick(card: CardView, items: ArrayList<Mark>, position: Int) {

                if (wantsToDelete) {


                    if (card.cardBackgroundColor == ContextCompat.getColorStateList(this@ViewMarks, R.color.cardColorDefault)) {

                        itemsToDelete.add(items[position].markTitle)
                        card.setCardBackgroundColor(ContextCompat.getColorStateList(this@ViewMarks, R.color.cardColorRed))

                    } else {

                        itemsToDelete.remove(items[position].markTitle)
                        card.setCardBackgroundColor(ContextCompat.getColorStateList(this@ViewMarks, R.color.cardColorDefault))

                    }


                } else if (wantsToEdit) {

                    wantsToEdit = false
                    val i = Intent(this@ViewMarks, EditMark::class.java)
                    i.putExtra(editDisciplineIntentKey, intent.getStringExtra(editDisciplineIntentKey))
                    i.putExtra(editMarkIntentKey, items[position].markId)
                    startActivity(i)

                }

            }
        })

    }

}