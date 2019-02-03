package com.apps.a7pl4y3r.catalogue.helpers

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.a7pl4y3r.catalogue.R

class RecyclerViewAdapterMarks(private val context: Context, private val items: ArrayList<Mark>) : RecyclerView.Adapter<RecyclerViewAdapterMarks.ViewHolder>() {


    private var listener: OnItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.card_main, parent, false), listener, items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = items[position].markTitle
        holder.subtitle.text = items[position].markSubtitle
    }

    override fun getItemCount(): Int = items.size


    interface OnItemClickListener {
        fun onItemClick(card: CardView, items: ArrayList<Mark>, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(view: View, listener: OnItemClickListener?, items: ArrayList<Mark>) : RecyclerView.ViewHolder(view) {

        val card: CardView = view.findViewById(R.id.card)
        val title: TextView = view.findViewById(R.id.cardTitle)
        val subtitle: TextView = view.findViewById(R.id.cardSubtitle)

        init {

            view.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION && listener != null)
                    listener.onItemClick(card, items, adapterPosition)

            }

        }

    }

}