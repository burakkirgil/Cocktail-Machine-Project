package com.burakkirgil.leblonapp

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Order : java.io.Serializable {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    init {
        auth = Firebase.auth
        firestore = Firebase.firestore
    }

    private var seat: String = ""
    private var branch: String = ""
    private var ingredient: String = ""

    fun setSeat(seat: String) {
        this.seat = seat
    }

    fun setBranch(branch: String) {
        this.branch = branch
    }

    fun setIngredient(ingredient: String) {
        this.ingredient = ingredient
    }

    fun makePayment(user: User): Boolean {
        if (user.getCart().getTotalPrice() <= user.getWallet().getAccountBalance()) {
            user.getWallet().setAccountBalance(
                user.getWallet().getAccountBalance() - user.getCart().getTotalPrice()
            )
            var balance : String = user.getWallet().getAccountBalance().toString()
            val updatedBalance = hashMapOf<String,String>()
            updatedBalance.put("walletBalance", balance)
            firestore.collection("Users").document(auth.currentUser!!.uid).set(updatedBalance)
            enterOrderIntoDB(auth.currentUser!!.uid)
            user.getCart().makeCartEmpty()
            return true
        } else {
            return false
        }
    }

    private fun enterOrderIntoDB(uid: String) {
        val order = hashMapOf<String, Any>()
        order.put("branch", branch)
        order.put("seat", seat)
        order.put("ingredient", ingredient)
        order.put("userID", uid)
        firestore.collection("Orders").add(order)

    }
}


