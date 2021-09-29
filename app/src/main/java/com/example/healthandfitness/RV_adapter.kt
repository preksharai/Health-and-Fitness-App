package com.example.healthandfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_item_look.view.*

//inheriting adapter class of recycler view class to use adapter
/* it is asking for viewholder in angular brackets so we'll say that it is present inside my class that is RV_adapter*/
//RV_adapter has two parameters in constructor
class RV_adapter(val items:ArrayList<ExerciseModel>,val context: Context) : RecyclerView.Adapter<RV_adapter.ViewHolder>()
{
    //creating viewholder, collection of views
    //it takes view parameter and inherits class RecyclerView.ViewHolder which gives view parameter to it
    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        //means make collection of tv of item
        //creating it's reference
        val tvitem=view.tv_item
    }

    //it asks for file(single_item_look) having that single item needed to be displayed
    //it returns viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //parent is parent of the view to be displayed, but not here,if no parent then attach to root is false
        //here, the textview is not under any layout, so it is parent of itself,therefore, write parent
        //LayoutInFlater is a class having method 'from'
        //inflater displays the item on screen
        //finally viewholder is created then returned
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_look,parent,false))
    }
    //asks for no. of items
    override fun getItemCount(): Int {
        return items.size
    }

    //called before each item is shown in the list means displayed..called as per the no. of items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //here, actually give the data to be displayed
        //model is object
        val model:ExerciseModel=items[position]
        holder.tvitem.text=model.id.toString()

        if(model.isSelected)
        {
            holder.tvitem.background =
                ContextCompat.getDrawable(context,R.drawable.selected_singleitemlook)
        }
        else if (model.isCompleted)
        {
            holder.tvitem.background =
                ContextCompat.getDrawable(context,R.drawable.completed_singleitemlook)
        }
        else
            //vo exercise nhi aai h abhi tk
            holder.tvitem.background =
                ContextCompat.getDrawable(context,R.drawable.single_item_background)


    }

}