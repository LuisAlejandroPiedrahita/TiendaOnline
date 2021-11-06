package co.edu.eam.disenosoftware.tienda.controllers

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import co.edu.eam.disenosoftware.tienda.servicios.ProductServicios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {

    @Autowired
    lateinit var productServicios: ProductServicios

    @PostMapping("/category/{id}")
    fun createProduct(@PathVariable("id")idCategory:Long, @RequestBody product: Product){
        productServicios.createProduct(product,idCategory)
    }

    @PutMapping("/{id}")
    fun editProduct(@PathVariable("id")idProduct:String,@RequestBody product: Product){
        product.id= idProduct
        productServicios.editProduct(product,idProduct)
    }
}