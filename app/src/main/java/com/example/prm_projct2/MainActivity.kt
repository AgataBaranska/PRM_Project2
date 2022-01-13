package com.example.prm_projct2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.prm_projct2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var btnLogIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnLogIn = binding.btnLogIn

        btnLogIn.setOnClickListener( View.OnClickListener {

                val intent = Intent(this,DisplayDataActivity::class.java)
                startActivity(intent)
        })



    }
}