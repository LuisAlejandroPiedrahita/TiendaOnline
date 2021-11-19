package co.edu.eam.disenosoftware.tienda.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
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
class CityController {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createCityAlreadyExist(){
        entityManager.persist(City(18,"Armenia"))
        val body = """
           {
            "id":1,
            "name":"Pereira"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This city already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createCityHappyPath(){
        val body = """
           {
            "id":1,
            "name":"Pereira"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/cities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)

    }

}