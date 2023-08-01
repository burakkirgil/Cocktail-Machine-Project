package com.burakkirgil.leblonapp

class Product(private var pId: String, private var quantity: Int) {
    fun getPId() : String{
        return pId
    }
    fun setQuantity(newQuantity : Int){
        this.quantity = newQuantity
    }
    fun getQuantity() : Int{
        return quantity
    }
}