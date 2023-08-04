package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.burakkirgil.leblonapp.databinding.ActivityMainBinding
import com.burakkirgil.leblonapp.databinding.ActivityMainBinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val intent = Intent(applicationContext, BranchActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun signInClicked(view : View) {

        val userEmail = binding.editTextEmail.text.toString()
        val password = binding.editTextCode.text.toString()

        if (userEmail.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(userEmail, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    //Signed In
                    firestore = Firebase.firestore
                    val intent = Intent(applicationContext, BranchActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(
                        applicationContext,
                        "Welcome: ${auth.currentUser?.email.toString()}",
                        Toast.LENGTH_LONG
                    ).show()


                }

            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}
