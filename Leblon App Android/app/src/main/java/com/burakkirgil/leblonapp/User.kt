package com.burakkirgil.leblonapp
class User() {
    private var userId : String? = null
    private var cart: Cart? = null
    private var wallet: Wallet? = null

    init {
    }
    fun setUserId(uid : String){
        cart = Cart(uid)
        wallet = Wallet(uid)
    }
    fun getUserId(): String {
        return userId!!
    }

    fun getCart(): Cart {
        return cart!!
    }

    fun getWallet(): Wallet {
        return wallet!!
    }

    fun logOut() {
        // FIREBASE LOGGING OUT
    }
}
