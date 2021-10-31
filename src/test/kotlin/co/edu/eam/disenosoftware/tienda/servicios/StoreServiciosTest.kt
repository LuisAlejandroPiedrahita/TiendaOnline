package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest

class StoreServiciosTest {

    @Autowired
    lateinit var storeServicios: StoreServicios

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testStoreCreate(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(Store("1","C/Juan n 5","Waxis",city))

        try{
            storeServicios.createStore(Store("1","C/Juan n 5","Waxis",city))
            Assertions.fail()
        } catch (e: BusinessException){
            Assertions.assertEquals("This Store already exists", e.message)
        }
    }

    @Test
    fun createStoreHappyPathTest(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store("1","C/Juan n 5","Waxis",city))

        val storeAssert = entityManager.find(Store::class.java,16L)
        Assertions.assertNotNull(storeAssert)

        Assertions.assertEquals("C/Juan n 5",storeAssert.address)
        Assertions.assertEquals("Waxis",storeAssert.name)
    }

    @Test
    fun editStoreNotExistTest(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val store = Store("1","C/Juan n 5","Waxis",city)

        try {
            storeServicios.editStore(store)
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("This store does not exist", e.message)
        }

    }

    @Test
    fun testStoreEdit(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        entityManager.persist(Store("1","C/Juan n 5","Waxis",city))

        val storeUpdate= (Store("2","Cra 17","Tienda videojuegos",city))
        storeServicios.editStore(storeUpdate)

        val storeUpdateAssert= entityManager.find(Store::class.java,"1")
        Assertions.assertEquals("Cra 17",storeUpdateAssert.address)
        Assertions.assertEquals("Tienda videojuegos",storeUpdateAssert.name)
    }
}