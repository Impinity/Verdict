package com.impinity.verdict

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class AddOptionsAdapter (private val myDataSet: MutableList<String>):
    RecyclerView.Adapter<AddOptionsAdapter.MyViewHolder>(){

    class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val name = view.findViewById(R.id.add_option_name) as TextView
        val remove = view.findViewById(R.id.add_option_remove) as ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): AddOptionsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_options_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = myDataSet[position];
        holder.remove.setOnClickListener {
            Log.v("ChoicesFragment", "position = $position | size = ${myDataSet.size}")
            myDataSet.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = myDataSet.size
}