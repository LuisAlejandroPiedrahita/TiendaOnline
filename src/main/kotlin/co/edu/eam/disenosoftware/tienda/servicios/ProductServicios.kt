package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import co.edu.eam.disenosoftware.tienda.repositorios.CategoryRepository
import co.edu.eam.disenosoftware.tienda.repositorios.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServicios {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createProduct(product: Product, idCategory: Long){

        val categoryById = categoryRepository.find(idCategory)

        if (categoryById == null){
            throw BusinessException("This category does not exist")
        }

        val productById = productRepository.find(product.id)

        if(productById != null){
            throw BusinessException("This Product already exists")
        }

        val productByNamE = productRepository.findByName(product.name)

        if(productByNamE != null){
            throw BusinessException("This Product already exists")
        }

        product.category = categoryById
        productRepository.create(product)
    }

    fun editProduct(product: Product, idProduct: String){
        val productById = productRepository.find(idProduct)

        if (productById == null) {
            throw BusinessException("This product does not exist")
        }

        val productByName = productRepository.findByName(product.name)

        if(productByName != null){
            throw BusinessException("This product with this name already exists")
        }

        product.category = productById.category
        productRepository.update(product)
    }

}