package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.burakkirgil.leblonapp.databinding.ActivityMenuBinding
import com.burakkirgil.leblonapp.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.updatedBalance.text = CurrentUser.user.getWallet().getAccountBalance().toString()
        val handler = Handler(Looper.getMainLooper())
        val delayMillis = 3000L

        handler.postDelayed({
            val intent = Intent(this, BranchActivity::class.java)
            startActivity(intent)
            }, delayMillis)

    }
}