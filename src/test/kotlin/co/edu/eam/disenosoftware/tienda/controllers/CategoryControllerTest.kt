package co.edu.eam.disenosoftware.tienda.controllers

import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.awt.PageAttributes
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createCategoryAlreadyExist(){
        entityManager.persist(Category(1,"Categoria_uno"))
        val body = """
           {
            "id":1,
            "name": "Aseo"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This category Already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createCategoryHappyPath() {
        val body = """
           {
            "id":1,
            "name": "Aseo"
            }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

}