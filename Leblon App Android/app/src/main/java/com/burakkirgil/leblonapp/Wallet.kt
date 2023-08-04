package com.burakkirgil.leblonapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Wallet(userId: String) {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var accountBalance: Double = 0.0


    init {
        auth = Firebase.auth
        firestore = Firebase.firestore
        val docRef = firestore.collection("Users").document(userId)
        docRef.get().addOnSuccessListener {
            val data = it.data
            val value = data?.get("walletBalance") as String
            if (value != null) {
                accountBalance = value.toDouble()
            }
        }

    }

    fun loadMoney(code: String) {
        if(code === "MIS49M") {

            setAccountBalance(accountBalance + 500)
            val updatedBalance = HashMap<String,String>()
            updatedBalance.put("walletBalance",accountBalance.toString())
            firestore.collection("Users").document(CurrentUser.user.getUserId()).set(updatedBalance)

        }
    }

    fun getAccountBalance(): Double {
        return accountBalance
    }

    fun setAccountBalance(newBalance: Double) {
        accountBalance = newBalance
    }
}