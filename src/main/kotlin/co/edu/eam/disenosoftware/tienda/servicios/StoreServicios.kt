package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Store
import co.edu.eam.disenosoftware.tienda.repositorios.CategoryRepository
import co.edu.eam.disenosoftware.tienda.repositorios.CityRepository
import co.edu.eam.disenosoftware.tienda.repositorios.ProductStoreRepository
import co.edu.eam.disenosoftware.tienda.repositorios.StoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StoreServicios {
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var cityRepository: CityRepository

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createStore(store: Store, idCity: Long){

        val city = cityRepository.find(idCity)

        if (city == null){
            throw BusinessException("This city does not exist")
        }

        val styoreById = storeRepository.find(store.id)

        if(styoreById != null){
            throw BusinessException("This Store already exists")
        }

        store.city = city
        storeRepository.create(store)
    }

    fun editStore(store: Store, idStore: Long) {
        val storeById = storeRepository.find(idStore)

        if (storeById == null) {
            throw BusinessException("This Store does not exist")
        }

        store.city = storeById.city
        storeRepository.update(store)
    }

    fun findProductsByStore(idStore: Long):List<Product>{

        val store = storeRepository.find(idStore)

        if (store == null){
            throw BusinessException("This store does not exist")
        }

        val listProductByStore = productStoreRepository.findByStore(idStore)
        return listProductByStore
    }

    fun findByStoreAndByCategory(idStore: Long, idCategory: Long):List<Product>{

        val store = storeRepository.find(idStore)

        if (store == null){
            throw BusinessException("This store does not exist")
        }
        val category = categoryRepository.find(idCategory)

        if(category == null){
            throw BusinessException("This category does not exist")
        }

        val listProductByStoreAndByCategory = productStoreRepository.findByStoreAndByCategory(idStore,idCategory)
        return listProductByStoreAndByCategory
    }

    fun findAllStores():List<Store>{
        val stores = storeRepository.listStore()
        return stores
    }
}