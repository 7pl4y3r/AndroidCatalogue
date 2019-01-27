package com.apps.a7pl4y3r.catalogue.helpers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.a7pl4y3r.catalogue.R

class RecyclerViewAdapter(private val items: ArrayList<Discipline>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(discipline: Discipline, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_main, viewGroup, false),
            listener, items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.subtitle.text = (items[position].marksCount)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View, listener: OnItemClickListener?, discipline: Discipline) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.cardTitle)
        val subtitle: TextView = view.findViewById(R.id.cardSubtitle)

        init {

            if (adapterPosition != RecyclerView.NO_POSITION && listener != null)
                listener.onItemClick(discipline, adapterPosition)

        }

    }

}