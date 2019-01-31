package com.apps.a7pl4y3r.catalogue.helpers

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.apps.a7pl4y3r.catalogue.R

class RecyclerViewAdapter(private val context: Context, private val items: ArrayList<Discipline>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_main, viewGroup, false),
            listener, items)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.card.setCardBackgroundColor(ContextCompat.getColorStateList(context, R.color.cardColorDefault))
        holder.title.text = items[position].title
        holder.subtitle.text = (items[position].marksCount)
    }

    override fun getItemCount(): Int = items.size


    interface OnItemClickListener {
        fun onItemClick(card: CardView, items: ArrayList<Discipline>, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(view: View, listener: OnItemClickListener?, items: ArrayList<Discipline>) : RecyclerView.ViewHolder(view) {

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