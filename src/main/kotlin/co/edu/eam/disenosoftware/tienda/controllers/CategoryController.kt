package co.edu.eam.disenosoftware.tienda.controllers

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import co.edu.eam.disenosoftware.tienda.servicios.CategoryServicios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController {

    @Autowired
    lateinit var categoryServicios: CategoryServicios

    @PostMapping
    fun create(@RequestBody category: Category){
        categoryServicios.createCategory(category)
    }
}