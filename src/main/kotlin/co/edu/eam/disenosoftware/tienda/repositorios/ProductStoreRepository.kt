package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import co.edu.eam.disenosoftware.tienda.modelos.Entities.ProductStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ProductStoreRepository {
    @Autowired
    lateinit var em: EntityManager

    fun create(productStore: ProductStore){
        em.persist(productStore)
    }


    fun find(id: Long): ProductStore?{
        return em.find(ProductStore::class.java, id)
    }

    fun update(productStore: ProductStore) {
        em.merge(productStore)
    }

    fun delete(id: Long) {
        val productStore = find(id)
        if (productStore!=null) {
            em.remove(productStore)
        }
    }

    fun findByStore(id: String):List<Product>{
        val query= em.createQuery("SELECT ProductStore.product FROM ProductStore ProductStore WHERE ProductStore.store.id =:idStore")
        query.setParameter("idStore",id)
        return query.resultList as List<Product>
    }


    fun findByStoreAndByCategory(id:Long,idCategory:Long):List<Product>{
        val query= em.createQuery("SELECT ProductStore.product FROM ProductStore ProductStore WHERE ProductStore.store.id =:idStore and ProductStore.product.category.id =:idCategory")
        query.setParameter("idStore",id)
        query.setParameter("idCategory",idCategory)
        return query.resultList as List<Product>
    }
}