package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class StoreRepositoryTest {
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateStore() {
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        storeRepository.create(Store(1L,"C/Juan n 5","Waxis",city))

        val store = entityManager.find(Store::class.java,  1L)
        Assertions.assertNotNull(store)
        Assertions.assertEquals(1L, store.id)
        Assertions.assertEquals("C/Juan n 5", store.address)
        Assertions.assertEquals("Waxis", store.name)
        Assertions.assertEquals("Armenia", store.city?.name)
    }

    @Test
    fun testDelete(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(Store(2L,"C/Juan n 5","Waxis",city))

        storeRepository.delete(2L)

        val store = entityManager.find(Store::class.java, 1L)
        Assertions.assertNull(store)
    }

    @Test
    fun findTest() {
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(Store(2L,"C/Juan n 5","Waxis",city))

        val store = storeRepository.find(2L)

        Assertions.assertNotNull(store)
        Assertions.assertEquals("Waxis", store?.name)
    }

    @Test
    fun testUpdate() {
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(Store(2L,"C/Juan n 5","Waxis",city))

        entityManager.flush()

        val store = entityManager.find(Store::class.java, 2L)
        store.address = "C/ Granada n 9"
        store.name = "Weling"

        entityManager.clear()

        storeRepository.update(store)

        val storeToAssert = entityManager.find(Store::class.java, 2L)
        Assertions.assertEquals("C/ Granada n 9", storeToAssert.address)
        Assertions.assertEquals("Weling", storeToAssert.name)
    }

    @Test
    fun listStoreTest(){
        val city= City(15L,"Armenia")
        entityManager.persist(city)
        val storeOne=Store(1L,"Cra 15","Tienda mascotas",city)
        val storeTwo=Store(2L,"Cra 16","Tienda helados",city)
        val storeThree= Store(3L,"Cra 18","Tienda videojuegos",city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(storeThree)

        val list= storeRepository.listStore()

        Assertions.assertEquals(3,list.size)
        Assertions.assertEquals("Tienda mascotas",list[0].name)
        Assertions.assertEquals("Cra 15",list[0].address)
        Assertions.assertEquals("Tienda helados",list[1].name)
        Assertions.assertEquals("Cra 16",list[1].address)
        Assertions.assertEquals("Tienda videojuegos",list[2].name)
        Assertions.assertEquals("Cra 18",list[2].address)
    }
}