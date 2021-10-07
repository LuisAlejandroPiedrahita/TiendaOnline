package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.City
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class CityRepositoryTest {
    @Autowired
    lateinit var cityRepository: CityRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateCity() {
        cityRepository.create(City(1,"Armenia"))

        val city = entityManager.find(City::class.java,  1)
        Assertions.assertNotNull(city)
        Assertions.assertEquals(1, city.id)
        Assertions.assertEquals("Armenia", city.name)
    }

    @Test
    fun testDelete(){
        entityManager.persist(City(1,"Armenia"))

        cityRepository.delete(1)

        val city = entityManager.find(City::class.java, 1)
        Assertions.assertNull(city)
    }

    @Test
    fun findTest() {
        entityManager.persist(City(1,"Armenia"))

        val city = cityRepository.find(1)

        Assertions.assertNotNull(city)
        Assertions.assertEquals("Armenia", city?.name)
    }

    @Test
    fun testUpdate() {
        entityManager.persist(City(1,"Armenia"))

        entityManager.flush()

        val city = entityManager.find(City::class.java, 1)
        city.name = "Cali"

        entityManager.clear()

        cityRepository.update(city)

        val cityToAssert = entityManager.find(City::class.java, 1)
        Assertions.assertEquals("Cali", cityToAssert.name)
    }
}