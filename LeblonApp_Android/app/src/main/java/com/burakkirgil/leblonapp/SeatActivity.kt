package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.burakkirgil.leblonapp.databinding.ActivityBranchBinding
import com.burakkirgil.leblonapp.databinding.ActivitySeatBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SeatActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySeatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intentFromBranch = intent
        val branch = intentFromBranch.getStringExtra("branch")
        binding.t1.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t1")
            startActivity(intent)
            finish()
        }
        binding.t2.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t2")
            startActivity(intent)
            finish()
        }
        binding.t3.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t3")
            startActivity(intent)
            finish()
        }
        binding.t4.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t4")
            startActivity(intent)
            finish()
        }
        binding.t5.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t5")
            startActivity(intent)
            finish()
        }
        binding.t6.setOnClickListener {
            val intent = Intent(this@SeatActivity, MenuActivity::class.java)
            intent.putExtra("branch",branch)
            intent.putExtra("seat","t6")
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