package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Product
import co.edu.eam.disenosoftware.tienda.modelos.Store
import co.edu.eam.disenosoftware.tienda.repositorios.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class ProductServicios {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createProduct(product: Product){
        val productById = productRepository.find(product.id)

        if(productById != null){
            throw BusinessException("This Product already exists")
        }

        val productByNamE = productRepository.find_name(product.name)

        if(productByNamE != null){
            throw BusinessException("This Product already exists")
        }

        productRepository.create(product)
    }

    fun editProduct(product: Product){
        val productById = productRepository.find(product.id)

        if (productById == null) {
            throw BusinessException("This product does not exist")
        }

        val productByName= productRepository.find_name(product.name)

        if(productByName!=null){
            throw BusinessException("This product with this name already exists")
        }

        productRepository.update(product)
    }

}