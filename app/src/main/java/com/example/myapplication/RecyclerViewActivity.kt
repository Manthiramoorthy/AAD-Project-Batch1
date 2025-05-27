package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = listOf(
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature),
            Person("Moorthy", R.drawable.nature),
            Person("Shyam", R.drawable.forest),
            Person("Siva", R.drawable.nature)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ProfileRecyclerViewAdapter(list)

        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )
    }
}