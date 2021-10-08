package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.City
import co.edu.eam.disenosoftware.tienda.modelos.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@SpringBootTest

class UserServiciosTest {

    @Autowired
    lateinit var userServicios: UserServicios

    @Autowired
    lateinit var entityManager: EntityManager
    @Test
    fun testUserCreate(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        try{
            userServicios.createUser(User("1","C/ Luna n 8","Alejo","Gomez",city))
            Assertions.fail()
        } catch (e:BusinessException){
            Assertions.assertEquals("This User already exists", e.message)
        }
    }

    @Test
    fun testUserEdit(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        val user = User("1","C/ Zoe n7","Luis","Piedrahita",city)

        try{
            userServicios.editUser(user)
            Assertions.fail()

        } catch (e:BusinessException){
            Assertions.assertEquals("This User already exists", e.message)
        }
    }
}