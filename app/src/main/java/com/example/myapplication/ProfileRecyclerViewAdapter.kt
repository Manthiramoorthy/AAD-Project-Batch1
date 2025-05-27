package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ProfileRecyclerViewAdapter(
    private val list: List<Person>
): RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.profileNameTextView)
        val imageView = view.findViewById<ImageView>(R.id.profileImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].name
        holder.imageView.setImageResource(list[position].res)
    }
}

