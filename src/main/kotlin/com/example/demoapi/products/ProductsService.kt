package com.example.demoapi.products

import org.springframework.stereotype.Service

@Service
class ProductsService {

    fun create(product: Product): Product = product

    fun list(): List<Product> {
        val products = mutableListOf<Product>()
        for (i in 1..2) { products.add(fakeProduct(i)) }
        return products
    }

    fun read(id: Int): Product? {
        if (listOf(1,2).contains(id)) { return fakeProduct(id) }
        return null
    }

    fun update(product: Product): Product? {
        if (listOf(1,2).contains(product.id)) {
            return product
        }
        return null
    }

    fun delete(id: String) {

    }

    private fun fakeProduct(id: Int): Product = Product(id = id, name = "Book Kotlin for Android Developers - ${id}st Edition", description = "Kotlin for Android Developers - ${id}st Edition: Learn Kotlin the easy way while developing an Android App")
}