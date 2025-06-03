package com.example.myapplication.notes.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.notes.Constants
import com.example.myapplication.notes.data.Note
import com.example.myapplication.notes.ui.NoteDetailsActivity

class NotesAdapter(private val list: List<com.example.myapplication.notes.local_db.Note>): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_custom_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = list[position].title
        holder.contentTextView.text = list[position].content
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NoteDetailsActivity::class.java)
            intent.putExtra(Constants.TITLE_KEY, list[position].title)
            intent.putExtra(Constants.CONTENT_KEY, list[position].content)
            intent.putExtra(Constants.ID_KEY, list[position].id)
            intent.putExtra(Constants.SOURCE_KEY, Constants.LIST_ITEM_VALUE)
            holder.itemView.context.startActivity(intent)
        }
    }
}