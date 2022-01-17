package com.example.prm_projct2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prm_projct2.databinding.ActivityMainBinding
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var btnLogIn: Button
    lateinit var tvRegister: TextView
    private lateinit var mCollbackManager: CallbackManager
    private lateinit var mFirebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFirebaseAuth = Firebase.auth

        btnLogIn = binding.btnLogIn
        tvRegister = binding.tvRegister

        btnLogIn.setOnClickListener( View.OnClickListener {

                val intent = Intent(this,DisplayDataActivity::class.java)
                startActivity(intent)
        })





    }

}