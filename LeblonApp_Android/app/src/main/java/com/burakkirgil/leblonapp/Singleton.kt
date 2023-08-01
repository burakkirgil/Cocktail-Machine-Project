package com.burakkirgil.leblonapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object CurrentUser {
    private lateinit var auth: FirebaseAuth
    var user: User = User()

    init {
        auth = Firebase.auth
        user.setUserId(auth.currentUser!!.uid)
    }
}
object ProductDetails {
    val productDetails: MutableMap<String, Double> = mutableMapOf()

    init {
        productDetails["MARGARITA"] = 120.0
        productDetails["MARTINI"] = 120.0
        productDetails["MOJITO"] = 115.0
        productDetails["BELLINI"] = 110.0
        productDetails["COSMOPOLITAN"] = 130.0
        productDetails["BLACKRUSSIAN"] = 150.0
        productDetails["JULEP"] = 130.0
        productDetails["LONGISLAND"] = 190.0
    }

}
