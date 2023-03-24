package com.example.footballquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.footballquiz.databinding.StartPageBinding

    class startPage : AppCompatActivity() {

        private lateinit var startButton: Button


        //private lateinit var binding: StartPageBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.start_page)

            startButton = findViewById(R.id.begin_the_quiz)

            /*binding = StartPageBinding.inflate(layoutInflater)
            setContentView(binding.root)*/

            /*binding.startButton.setOnClickListener { view: View ->
                val intent = Intent(this, MainActivity::class.java)
            }*/


            startButton.setOnClickListener { view: View ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }



