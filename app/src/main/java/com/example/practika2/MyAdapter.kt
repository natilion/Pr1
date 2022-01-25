package com.example.practika2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val itemList: ArrayList<Item>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]

        holder.name.text = currentitem.name
        holder.vendercode.text = currentitem.vendorcode
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val name : TextView = itemView.findViewById(R.id.name)
        val vendercode : TextView = itemView.findViewById(R.id.vendercode)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}