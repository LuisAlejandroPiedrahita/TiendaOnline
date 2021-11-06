package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
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

    fun find(id: String?): Product?{
        return em.find(Product::class.java, id)
    }

    fun update(product: Product) {
        em.merge(product)
    }

    fun delete(id: String) {
        val product = find(id)
        if (product != null) {
            em.remove(product)
        }
    }

    fun findByName(name: String): Product? {
        val query = em.createQuery("SELECT product FROM Product product WHERE product.name = :name")
        query.setParameter("name", name)

        val list = query.resultList as List<Product>

        //asignacion condicional
        return if (list.isEmpty()) null else list[0]
    }

}