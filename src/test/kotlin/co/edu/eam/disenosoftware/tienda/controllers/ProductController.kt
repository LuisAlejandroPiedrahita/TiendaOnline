package co.edu.eam.disenosoftware.tienda.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Category
import co.edu.eam.disenosoftware.tienda.modelos.Entities.Product
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
class ProductController {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductCategoryDoesNotExist(){
        val body = """
           {
            "id": "111",
            "name": "De-todito",
            "branch": "Margarita"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This category does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductAlreadyExist(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("111","Jabon","Avon",category))
        val body = """
           {
            "id": "111",
            "name": "De_todito",
            "branch": "Margarita"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductAlreadyExistWithThisName(){
        val category = Category(1,"Comida")
        entityManager.persist(category)
        entityManager.persist(Product("555","Jabon","Avon",category))
        val body = """
           {
            "id": "111",
            "name": "Jabon",
            "branch": "Derby"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductHappyPath(){
        val category = Category(1,"Comida")
        entityManager.persist(category)
        val body = """
           {
            "id": "111",
            "name": "De_todito",
            "branch": "Margarita"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/products/category/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun editProductDoesNotExist(){
        val body = """
           {
            "name": "Detergente",
            "branch": "Derby"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editProductWithTheNameAlreadyExist(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("555","Jabon","Avon",category))
        val body = """
           {
            "name": "Jabon",
            "branch": "Derby"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/555")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product with this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editProductHappyPath(){
        val category= Category(1,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("555","Jabon","Avon",category))
        val body = """
           {
            "name": "Shampoo",
            "branch": "Avon"
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/products/555")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val product= entityManager.find(Product::class.java,"555")
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("Shampoo",product.name)
        Assertions.assertEquals("Avon",product.branch)
    }

}