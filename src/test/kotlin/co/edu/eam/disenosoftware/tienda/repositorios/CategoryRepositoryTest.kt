package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class CategoryRepositoryTest {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateCategory() {

        categoryRepository.create(Category(1,"categoria_uno"))

        val category = entityManager.find(Category::class.java,  1)
        Assertions.assertNotNull(category)
        Assertions.assertEquals(1, category.id)
        Assertions.assertEquals("categoria_uno", category.name)
    }

    @Test
    fun testDelete(){
        entityManager.persist(Category(1,"categoria_uno"))

        categoryRepository.delete(1)

        val category = entityManager.find(Category::class.java, 1)
        Assertions.assertNull(category)
    }

    @Test
    fun findTest() {
        entityManager.persist(Category(1,"categoria_uno"))

        val category = categoryRepository.find(1)

        Assertions.assertNotNull(category)
        Assertions.assertEquals("categoria_uno", category?.name)
    }

    @Test
    fun testUpdate() {
        entityManager.persist(Category(1,"categoria_uno"))

        entityManager.flush()

        val category = entityManager.find(Category::class.java, 1)
        category.name = "categoria_dos"

        entityManager.clear()

        categoryRepository.update(category)

        val categoryToAssert = entityManager.find(Category::class.java, 1)
        Assertions.assertEquals("categoria_dos", categoryToAssert.name)
    }
}