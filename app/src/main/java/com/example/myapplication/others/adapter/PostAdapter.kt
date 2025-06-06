package com.example.myapplication.others.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.NoteCustomViewBinding
import com.example.myapplication.others.api.Post

class PostAdapter(val list: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(binding: NoteCustomViewBinding): RecyclerView.ViewHolder(binding.root){
        val titleTextView = binding.titleTextView
        val contentTextView = binding.contentTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoteCustomViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = list[position].title
        holder.contentTextView.text = list[position].body
    }
}