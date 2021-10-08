package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.City
import co.edu.eam.disenosoftware.tienda.modelos.Store
import co.edu.eam.disenosoftware.tienda.modelos.User
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
    fun testStoreEdit(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        val store = Store("1","C/Juan n 5","Waxis",city)

        try{
            storeServicios.editStore(store)
            Assertions.fail()

        } catch (e: BusinessException){
            Assertions.assertEquals("This Store already exists", e.message)
        }
    }
}