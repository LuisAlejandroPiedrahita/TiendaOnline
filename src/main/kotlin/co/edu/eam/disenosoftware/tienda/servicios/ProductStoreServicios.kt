package co.edu.eam.disenosoftware.tienda.servicios
import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.ProductStore
import co.edu.eam.disenosoftware.tienda.repositorios.ProductStoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductStoreServicios {
    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    fun createProductInStore(productStore: ProductStore){
        val list = productStoreRepository.findByStore(productStore.store.id)

        list.forEach { if(it.id==productStore.product.id){throw BusinessException("This product already exists in this store")
        } }

        productStoreRepository.create(productStore)
    }

    fun decreaseStock(idProductStore: Long,decreaseStock:Double){
        val productStore = productStoreRepository.find(idProductStore)

        if(productStore == null){
            throw BusinessException("This product store does not exist")
        }

        if (decreaseStock> productStore.stock){
            throw BusinessException("there can be no less than zero stocks")
        }

        productStore.stock= productStore.stock-decreaseStock
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