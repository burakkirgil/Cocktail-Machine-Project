package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakkirgil.leblonapp.databinding.ActivityMenuBinding
import com.burakkirgil.leblonapp.databinding.ActivityPaymentBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class PaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val intentFromMenu = intent
        val branch = intentFromMenu.getStringExtra("branch")
        val seat = intentFromMenu.getStringExtra("seat")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val cartAdapter = CurrentUser.user?.getCart()
            ?.let { CartAdapter(productList = it.getProducts()) }
        binding.recyclerView.adapter = cartAdapter
        binding.totalAmount.text = CurrentUser.user?.getCart()?.getTotalPrice().toString() + "TL"

        val delayMillis = 750L // 0.5 saniye

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            while (true) {
                binding.totalAmount.text =
                    CurrentUser.user?.getCart()?.getTotalPrice().toString() + "TL"
                delay(delayMillis)
            }
        }
        binding.pay.setOnClickListener {
            val order = Order()
            order.setBranch(branch!!)
            order.setSeat(seat!!)
            val productsFinal = CurrentUser.user.getCart().getProducts()
            var ingredients: String = ""
            for (product in productsFinal) {
                ingredients += "${product.getPId()}-${product.getQuantity()}/"
            }
            order.setIngredient(ingredients)
            if (order.makePayment(CurrentUser.user)) {
                val intent = Intent(this@PaymentActivity, SuccessActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(
                    this,
                    "Insufficient account balance, please load money your wallet.",
                    Toast.LENGTH_LONG
                ).show()
            }
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





