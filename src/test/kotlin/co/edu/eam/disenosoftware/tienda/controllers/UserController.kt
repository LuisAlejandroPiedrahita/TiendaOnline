package co.edu.eam.disenosoftware.tienda.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import co.edu.eam.disenosoftware.tienda.modelos.Entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserController {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createUserCityDoesNotExist(){
        val body = """
           {
            "id":"111",
            "address":"C/11",
            "name": "Luis",
            "lastName": "Gomez"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/222")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This city does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createUserAlreadyExist(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("111","C/5","Luis","Gomez",city))
        val body = """
           {
            "id":"111",
            "address":"C/11",
            "name": "Luis Alejandro",
            "lastName": "Gomez"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"this user already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createUserHappyPath(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        val body = """
           {
            "id":"111",
            "address":"C/5",
            "name": "Luis",
            "lastName": "Gomez"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/users/cities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun editUserDoesNotExist(){
        val body = """
           {
            "address":"C/12",
            "name": "Luis",
            "lastName": "Gomez"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/users/222")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This user does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editUserHappyPath(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        entityManager.persist(User("111","C/5","Luis","Gomez",city))
        val body = """
           {
            "address":"C/11",
            "name": "Alejandro",
            "lastName": "Piedrahita"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/users/111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val user= entityManager.find(User::class.java,"111")
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("C/11",user.address)
        Assertions.assertEquals("Alejandro",user.name)
    }

}