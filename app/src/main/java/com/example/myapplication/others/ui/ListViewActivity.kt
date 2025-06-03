package com.example.myapplication.others.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.others.adapter.ProfileAdapter
import com.example.myapplication.R

class ListViewActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.listView)

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
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        val adapter = ProfileAdapter(list)
        listView.adapter = adapter


        listView.setOnItemClickListener { adapterView, view, position, l ->
            Toast.makeText(this, "item clicked" + list[position], Toast.LENGTH_LONG).show()
        }
    }
}

class Person(val name: String, val res: Int) {

}
