package com.example.myapplication.others.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.others.adapter.ProfileRecyclerViewAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRecyclerViewBinding
import com.example.myapplication.others.common.Constant

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerViewBinding
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 102 && resultCode == RESULT_OK) {
            val uri = data?.data
            binding.profilePic.setImageURI(uri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.logoutButton.setOnClickListener {
            val sharedPref = getSharedPreferences(Constant.SHAREDPREF_NAME, MODE_PRIVATE)
            sharedPref.edit {
//                remove(Constant.IS_LOGGED_IN_KEY) // remove only the IS_LOGGED_IN_KEY
                clear() // remove all the content
            }
            onBackPressedDispatcher.onBackPressed()
        }

        binding.uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 102)
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


        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = ProfileRecyclerViewAdapter(list)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val username = intent.getStringExtra(Constant.USERNAME_KEY)
        binding.textUserName.text = username
    }
}