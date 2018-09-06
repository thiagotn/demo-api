package com.example.demoapi.products

import org.springframework.stereotype.Service

@Service
class ProductsService {

    fun create(product: Product): Product = product

    fun list(): List<Product> {
        val products = mutableListOf<Product>()
        for (i in 1..2) { products.add(mockProduct(i)) }
        return products
    }

    fun read(id: Int): Product? {
        return when (id) {
            1,2 -> mockProduct(id)
            else -> null
        }
    }

    fun update(product: Product): Product? {
        return when (product.id) {
            1,2 -> product
            else -> null
        }
    }

    fun delete(id: Int) {

    }

    private fun mockProduct(id: Int): Product = Product(id = id, name = "Book Kotlin for Android Developers - ${id}st Edition", description = "Kotlin for Android Developers - ${id}st Edition: Learn Kotlin the easy way while developing an Android App")
}