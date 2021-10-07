package co.edu.eam.disenosoftware.tienda.repositorios

import co.edu.eam.disenosoftware.tienda.modelos.City
import co.edu.eam.disenosoftware.tienda.modelos.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateUser() {
        val city = City(1,"Armenia")
        entityManager.persist(city)

        userRepository.create(User("1","C/ Luna n 8","Alejo","Gomez",city))

        val user = entityManager.find(User::class.java,  "1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("1", user.id)
        Assertions.assertEquals("C/ Luna n 8", user.address)
        Assertions.assertEquals("Alejo", user.name)
        Assertions.assertEquals("Gomez", user.lastName)
        Assertions.assertEquals("Armenia", user.city.name)
    }

    @Test
    fun testDelete(){
        val city = City(1,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        userRepository.delete("1")

        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNull(user)
    }

    @Test
    fun findTest() {
        val city = City(1,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        val user = userRepository.find("1")

        Assertions.assertNotNull(user)
        Assertions.assertEquals("Alejo", user?.name)
    }

    @Test
    fun testUpdate() {
        val city = City(1,"Armenia")
        entityManager.persist(city)

        entityManager.persist(User("1","C/ Luna n 8","Alejo","Gomez",city))

        entityManager.flush()

        val user = entityManager.find(User::class.java, "1")
        user.name = "Luis"
        user.lastName = "Piedrahita"
        user.address = "C/ Zoe n7"
        user.city.name = "Cali"

        entityManager.clear()

        userRepository.update(user)

        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertEquals("Luis", userToAssert.name)
        Assertions.assertEquals("Piedrahita", userToAssert.lastName)
        Assertions.assertEquals("C/ Zoe n7", userToAssert.address)
        Assertions.assertEquals("Cali", userToAssert.city.name)
    }
}