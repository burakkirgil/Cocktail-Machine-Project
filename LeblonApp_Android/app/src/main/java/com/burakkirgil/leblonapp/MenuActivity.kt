package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.burakkirgil.leblonapp.databinding.ActivityMenuBinding
import com.burakkirgil.leblonapp.databinding.ActivitySeatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMenuBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth
        firestore = Firebase.firestore

        val intentFromSeat = intent
        val branch = intentFromSeat.getStringExtra("branch")
        val seat = intentFromSeat.getStringExtra("seat")
        binding.MARTINI.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("MARTINI", 1)
        }
        binding.MARGARITA.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("MARGARITA", 1)
        }
        binding.BELLINI.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("BELLINI", 1)
        }
        binding.BLACKRUSSIAN.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("BLACKRUSSIAN", 1)
        }
        binding.MOJITO.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("MOJITO", 1)
        }
        binding.COSMOPOLITAN.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("COSMOPOLITAN", 1)
        }
        binding.JULEP.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("JULEP", 1)
        }
        binding.LONGISLAND.setOnClickListener {
            CurrentUser.user?.getCart()?.addProduct("LONGISLAND", 1)
        }
        binding.cart.setOnClickListener {
            val intent = Intent(this@MenuActivity, PaymentActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat",seat)
            startActivity(intent)
            finish()
        }
        binding.wallet.setOnClickListener {
            val intent = Intent(this, WalletActivity::class.java)
            startActivity(intent)
        }
        binding.logout.setOnClickListener{
            Firebase.auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}