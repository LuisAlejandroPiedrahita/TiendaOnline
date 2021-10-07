package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ProductRepository {

    @Autowired
    lateinit var em: EntityManager

    fun create(product: Product){
        em.persist(product)
    }


    fun find(id: Long): Product?{
        return em.find(Product::class.java, id)
    }

    fun listNames(id: Long):List<String>{
        return listOf(em.find(Product::class.java,id).name)
    }

    fun update(product: Product) {
        em.merge(product)
    }

    fun delete(id: Long) {
        val product = find(id)
        if (product!=null) {
            em.remove(product)
        }
    }

}