package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import co.edu.eam.disenosoftware.tienda.repositorios.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServicios {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createCategory(category: Category){
        val categoryById = categoryRepository.find(category.id)

        if (categoryById != null){
            throw BusinessException("This category Already exist")
        }
        categoryRepository.create(category)
    }
}