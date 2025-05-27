package com.example.myapplication

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ProfileAdapter(private val list: List<Person>) : BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        Log.d("ProileAdapter", "View id" + view?.id)
        val customView = LayoutInflater.from(viewGroup?.context) // object for LayoutInflater
            .inflate(R.layout.custom_item_view, viewGroup, false)

        //Set the profile name
        val textView = customView.findViewById<TextView>(R.id.profileNameTextView)
        textView.text = list[position].name

        //Set the profile picture
        val imageView = customView.findViewById<ImageView>(R.id.profileImageView)
        imageView.setImageResource(list[position].res)

        return customView
    }
}