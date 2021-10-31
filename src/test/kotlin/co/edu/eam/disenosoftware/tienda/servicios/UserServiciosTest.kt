package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import co.edu.eam.disenosoftware.tienda.modelos.Entities.User
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
    fun createUserHappyPathTest(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        val userAssert=entityManager.find(User::class.java,"1")
        Assertions.assertNotNull(userAssert)

        Assertions.assertEquals("C/ Luna n 8",userAssert.address)
        Assertions.assertEquals("Alejo",userAssert.name)
        Assertions.assertEquals("Gomez",userAssert.lastName)
    }

    @Test
    fun editUserNotExistTest(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        val user = User("1","C/ Luna n 8","Alejo","Gomez",city)

        try {
            userServicios.editUser(user)
            Assertions.fail()

        }catch (e: BusinessException) {
            Assertions.assertEquals("This user does not exist", e.message)
        }

    }

    @Test
    fun testUserEdit(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        val userUpdate= User("1","Cra 22","Julian","Beltran",city)
        userServicios.editUser(userUpdate)

        val userUpdateAssert= entityManager.find(User::class.java,"1")
        Assertions.assertEquals("Julian",userUpdateAssert.name)
        Assertions.assertEquals("Beltran",userUpdateAssert.lastName)
    }
}