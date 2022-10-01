package com.example.internetex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        findViewById<Button>(R.id.query).setOnClickListener {
            if (findViewById<EditText>(R.id.username).text.isNotEmpty()) {
                viewModel.name = findViewById<EditText>(R.id.username).text.toString()
                viewModel.response.observe(this) {
                    findViewById<TextView>(R.id.textView).text = it
                }
                viewModel.refreshData()
            }
        }
    }
}
