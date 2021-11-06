package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.ProductStore
import co.edu.eam.disenosoftware.tienda.modelos.Request.ProductRequest
import co.edu.eam.disenosoftware.tienda.repositorios.ProductRepository
import co.edu.eam.disenosoftware.tienda.repositorios.ProductStoreRepository
import co.edu.eam.disenosoftware.tienda.repositorios.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductStoreServicios {
    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    fun createProductInStore(productRequest: ProductStore){

        val product = productRepository.find(productRequest.idProduct)

        if (product == null){
            throw BusinessException("This product does not exits")
        }

        val store = storeRepository.find(productRequest.idStore)

        if(store == null){
            throw BusinessException("This store does not exist")
        }
        val list = productStoreRepository.findByStore(productRequest.idStore)

        list.forEach { if(it.id == productRequest.idProduct){throw BusinessException("This product already exists in this store")
        } }

        val productStore = ProductStore(productRequest.id,productRequest.price,productRequest.stock,
            product,store)
        productStoreRepository.create(productStore)
    }

    fun decreaseStock(idProductStore: Long,decreaseStock:Double){
        val productStore = productStoreRepository.find(idProductStore)

        if(productStore == null){
            throw BusinessException("This product store does not exist")
        }

        if (decreaseStock > productStore.stock){
            throw BusinessException("there can be no less than zero stocks")
        }

        productStore.stock = productStore.stock-decreaseStock
        productStoreRepository.update(productStore)
    }

    fun increaseStock(idProductStore: Long,increaseStock:Double){

        val productStore = productStoreRepository.find(idProductStore)

        if(productStore == null){
            throw BusinessException("This product store does not exist")
        }
        productStore.stock = productStore.stock+increaseStock
        productStoreRepository.update(productStore)
    }
}