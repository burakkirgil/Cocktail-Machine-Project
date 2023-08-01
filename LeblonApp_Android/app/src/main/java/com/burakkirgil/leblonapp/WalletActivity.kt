package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.burakkirgil.leblonapp.databinding.ActivityMenuBinding
import com.burakkirgil.leblonapp.databinding.ActivityWalletBinding

class WalletActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.currentBalance.text = CurrentUser.user.getWallet().getAccountBalance().toString()
        binding.load.setOnClickListener {
            CurrentUser.user.getWallet().setAccountBalance(CurrentUser.user.getWallet().getAccountBalance() + 500)
            CurrentUser.user.getWallet().loadMoney(binding.editTextPassword.toString())
            binding.currentBalance.text = CurrentUser.user.getWallet().getAccountBalance().toString()
            Toast.makeText(
                this,
                "Your current wallet balance is ${CurrentUser.user.getWallet().getAccountBalance()}.",
                Toast.LENGTH_LONG
            ).show()
            finish()

        }
    }
}