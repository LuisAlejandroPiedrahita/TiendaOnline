package co.edu.eam.disenosoftware.tienda.controllers

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Store
import co.edu.eam.disenosoftware.tienda.modelos.Request.ProductRequest
import co.edu.eam.disenosoftware.tienda.servicios.ProductStoreServicios
import co.edu.eam.disenosoftware.tienda.servicios.StoreServicios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stores")
class StoreController {

    @Autowired
    lateinit var productStoreServicios: ProductStoreServicios

    @Autowired
    lateinit var storeServicios: StoreServicios

    @PostMapping("/product")
    fun createProductInStore(@RequestBody productRequest: ProductRequest){
        productStoreServicios.createProductInStore(productRequest)
    }

    @PostMapping("/cities/{id}")
    fun createStore(@PathVariable("id") idCity:Long, @RequestBody store: Store){
        storeServicios.createStore(store,idCity)
    }

    @PutMapping("{id}")
    fun editStore(@PathVariable("id") idStore:Long,@RequestBody store: Store){
        store.id = idStore
        storeServicios.editStore(store,idStore)
    }

    @GetMapping()
    fun findAllStores():List<Store>{
        val stores = storeServicios.findAllStores()
        return stores
    }

    @GetMapping("{id}/products")
    fun findProductByStore(@PathVariable("id") idStore: String):List<Product>{
        val listProductByStore = storeServicios.findProductsByStore(idStore)
        return listProductByStore
    }

    @GetMapping("{id}/categories/{idCategory}/products")
    fun findByStoreAndByCategory(@PathVariable("id")idStore: String,
                                 @PathVariable("idCategory") idCategory:Long):List<Product>{
        val listProductByStoreAndByCategory = storeServicios.findByStoreAndByCategory(idStore,idCategory)
        return listProductByStoreAndByCategory
    }
}