package com.example.healthandfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item_look.view.*

class HistoryAdapter(val context: Context,val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val ll_item=view.linearlayoutitem
        val textViewPosition=view.textviewposition
        val textViewDate=view.textviewdate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_look,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var date = items[position]
        holder.textViewPosition.text = (position + 1).toString() //bcz it started from zero
        holder.textViewDate.text = date
    }
}