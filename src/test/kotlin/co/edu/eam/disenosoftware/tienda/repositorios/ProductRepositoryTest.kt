package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class ProductRepositoryTest {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateProduct() {
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        productRepository.create(Product(1L,"jugo","nestle",category))

        val product = entityManager.find(Product::class.java,  1L)
        Assertions.assertNotNull(product)
        Assertions.assertEquals(1L, product.id)
        Assertions.assertEquals("jugo", product.name)
        Assertions.assertEquals("nestle", product.branch)
        Assertions.assertEquals("categoria_uno", product.category.name)
    }

    @Test
    fun testDelete(){
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        entityManager.persist(Product(1L,"jugo","nestle",category))

        productRepository.delete(1L)

        val product = entityManager.find(Product::class.java, 1L)
        Assertions.assertNull(product)
    }

    @Test
    fun findTest() {
        val category = Category(1,"categoria_uno")
        entityManager.persist(category)

        entityManager.persist(Product(1,"jugo","nestle",category))

        val product = productRepository.find(1)

        Assertions.assertNotNull(product)
        Assertions.assertEquals("jugo", product?.name)
        Assertions.assertEquals("nestle",product?.branch)
        Assertions.assertEquals(1L,product?.category?.id)
        Assertions.assertEquals("categoria_uno",product?.category?.name)
    }

    @Test
    fun testUpdate() {
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)

        entityManager.persist(Product(1L,"jugo","nestle",category))

        entityManager.flush()

        val product = entityManager.find(Product::class.java, 1L)
        product.name = "leche"
        product.branch = "alqueria"
        product.category.name = "categoria_dos"

        entityManager.clear()

        productRepository.update(product)

        val productToAssert = entityManager.find(Product::class.java, 1)
        Assertions.assertEquals("leche", productToAssert.name)
        Assertions.assertEquals("alqueria", productToAssert.branch)
        Assertions.assertEquals("categoria_dos", productToAssert.category.name)
    }

    @Test
    fun findByName(){
        val category = Category(1L,"categoria_uno")
        entityManager.persist(category)
        entityManager.persist(Product(1L,"jugo","nestle",category))

        val product= productRepository.findByName("jugo")

        Assertions.assertNotNull(product)
        Assertions.assertEquals("jugo",product?.name)
        Assertions.assertEquals("nestle",product?.branch)
        Assertions.assertEquals(1L,product?.category?.id)
        Assertions.assertEquals("categoria_uno",product?.category?.name)
    }

}