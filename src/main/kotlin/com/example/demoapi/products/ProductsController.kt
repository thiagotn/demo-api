package com.example.demoapi.products

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RequestMapping("/products")
@RestController
class ProductsController(private val service: ProductsService) {

    @PostMapping
    fun create(@RequestBody product: Product): ResponseEntity<Any> {
        val created = service.create(product)
        return ResponseEntity.created(buildURI(created.id)).build()
    }

    @GetMapping()
    fun list(): ResponseEntity<Any> {
        return ResponseEntity.ok(service.list())
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): ResponseEntity<Any> {
        val product = service.read(id) ?: return ResponseEntity.notFound().build<Any>()
        return ResponseEntity.ok(product)
    }

    @PatchMapping()
    fun update(@RequestBody product: Product): ResponseEntity<Any> {
        val updated = service.update(product) ?: return ResponseEntity.notFound().build<Any>()
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        service.delete(id)
        return ResponseEntity.ok().build()
    }

    private fun buildURI(id: Int):URI = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri()
}