package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Category
import co.edu.eam.disenosoftware.tienda.modelos.City
import co.edu.eam.disenosoftware.tienda.modelos.Product
import co.edu.eam.disenosoftware.tienda.modelos.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
class ProductServiciosTest {

    @Autowired
    lateinit var productServicos: ProductServicios

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testProductCreate(){
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        entityManager.persist(Product(1L,"jugo","nestle",category))

        try{
            productServicos.createProduct(Product(1,"jugo","nestle",category))
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This Product already exists", e.message)
        }
    }

    @Test
    fun testProductEdit() {
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        val product = Product(1L,"jugo","nestle",category)

        try{
            productServicos.editProduct(product)
            Assertions.fail()

        } catch (e:BusinessException){
            Assertions.assertEquals("This product does not exist", e.message)
        }
    }
}