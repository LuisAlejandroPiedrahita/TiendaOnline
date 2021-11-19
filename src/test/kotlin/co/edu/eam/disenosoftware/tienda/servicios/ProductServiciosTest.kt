package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
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
    lateinit var productServicios: ProductServicios

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testProductCreate(){
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        entityManager.persist(Product("1","jugo","nestle",category))

        try{
            productServicios.createProduct(Product("1","jugo","nestle",category),1L)
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This Product already exists", e.message)
        }
    }

    @Test
    fun createProductRepeatedNameTest() {
        //prerequisitos
        val category = Category(1L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { productServicios.createProduct(Product("2", "Detergente", "Ariel", category), 1L) }
        )

        Assertions.assertEquals("This product with this name already exists", exception.message)
    }

    @Test
    fun createProductHappyPathTest() {
        val category = Category(1L,"alimento")
        entityManager.persist(category)
        productServicios.createProduct(Product("1","Detergente","Ariel",category), 1L)

        //verificaciones..
        val productToAssert = entityManager.find(Product::class.java, "1")
        Assertions.assertNotNull(productToAssert)

        Assertions.assertEquals("Detergente", productToAssert.name)
    }

    @Test
    fun editProductNotExistTest(){
        val category = Category(1L,"alimento")
        entityManager.persist(category)
        val productUpdate = Product("1","Detergente","Ariel",category)

        try{
            productServicios.editProduct(productUpdate, "1")
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This product does not exist", e.message)
        }
    }

    @Test
    fun editProductRepeatedNameTest(){
        val category = Category(1L,"alimento")
        entityManager.persist(category)
        entityManager.persist(Product("1","Detergente","Ariel",category))
        val productUpdate = Product("1","Detergente","Ariel",category)
        try{
            productServicios.editProduct(productUpdate, "1")
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This product with this name already exists", e.message)
        }
    }

    @Test
    fun testProductEdit() {
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        val product = Product("1","jugo","nestle",category)

        val productUpdate= Product("1","Papitas","Ariel",category)
        productServicios.editProduct(productUpdate, "1")

        val productToAssert = entityManager.find(Product::class.java, "1")
        Assertions.assertNotNull(productToAssert)

        Assertions.assertEquals("Papitas", productToAssert.name)
    }
}