package com.burakkirgil.leblonapp

import com.burakkirgil.leblonapp.Product
import com.burakkirgil.leblonapp.ProductDetails

class Cart(userId: String) {
    private var products: ArrayList<Product>? = null
    private var totalPrice: Double = 0.0




    fun addProduct(pId: String, quantity: Int) {
        if (products != null) {
            var status = true
            for (i in products!!.indices) {
                if (products!![i].getPId().compareTo(pId) == 0){
                    products!![i].setQuantity(products!![i].getQuantity() + 1)
                    totalPrice += getProductPriceFromProductDetails(pId)
                    status = false
                }
            }
            if(status){
                products!!.add(Product(pId,quantity))
                totalPrice += getProductPriceFromProductDetails(pId)

            }
        }
        else {
            products = ArrayList<Product>()
            products!!.add(Product(pId, quantity))

            totalPrice += getProductPriceFromProductDetails(pId)

        }
    }

    fun decreaseQuantity(pId: String) {
        for (i in products!!.indices) {
            if (products!![i].getPId().compareTo(pId) == 0){
                products!![i].setQuantity(products!![i].getQuantity() - 1)
                totalPrice -= getProductPriceFromProductDetails(pId)
            }
        }
    }

    fun makeCartEmpty() {
        products!!.clear()
        totalPrice = 0.0
    }

    fun getTotalPrice(): Double {
        return totalPrice
    }

    private fun getProductPriceFromProductDetails(pId: String): Double {
        return ProductDetails.productDetails!!.get(pId)!!
    }

    fun getProducts(): ArrayList<Product> {
        return products!!

    }
}
