package co.edu.eam.disenosoftware.tienda.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import co.edu.eam.disenosoftware.tienda.exceptions.ErrorResponse
import co.edu.eam.disenosoftware.tienda.modelos.Entities.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StoreController {

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductInStoreProductDoesNotExist(){
        val body = """
           {
            "id":1,
            "price":2000.00,
            "stock":30.0,
            "idProduct": "111",
            "idStore": 222
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product does not exits\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreThisStoreDoesNotExist(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        entityManager.persist(Product("555","Jabon","Avon",category))
        val body = """
           {
            "id":1,
            "price":2000.00,
            "stock":30.0,
            "idProduct": "555",
            "idStore": 222
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreThisProductAlreadyExistInStore(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        val product = Product("555","Jabon","Avon",category)
        entityManager.persist(product)
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val store = Store(1,"C/10","Exito",city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(1,1500.0,18.0,product,store))
        val body = """
           {
            "id":1,
            "price":2000.00,
            "stock":30.0,
            "idProduct": "555",
            "idStore": 1
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product already exists in this store\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createProductInStoreHappyPath(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        val product = Product("555","Jabon","Avon",category)
        entityManager.persist(product)
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val store = Store(1,"C/10","Exito",city)
        entityManager.persist(store)
        val body = """
           {
            "id":1,
            "price":2000.0,
            "stock":30.0,
            "idProduct": "555",
            "idStore": 1
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createStoreCityDoesNotExist(){
        val body = """
           {
            "id":1,
            "address":"C/10'",
            "name":"Exito"            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/18")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This city does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createStoreAlreadyExist(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        val store = Store(1,"C/11","Ara",city)
        entityManager.persist(store)
        val body = """
           {
            "id":1,
            "address":"C/10",
            "name":"Exito"            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"this store already exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun createStoreHappyPath(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        val body = """
           {
            "id":1,
            "address":"C/10",
            "name":"Exito"            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .post("/stores/cities/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun editStoreDoesNotExist(){
        val body = """
           {             
            "address":"C/15",
            "name":"Exito"            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/stores/111")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This store does not exist\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editStoreHappyPath(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        val store = Store(1L,"C/10","Exito",city)
        entityManager.persist(store)
        val body = """
           {
            "address":"C/15",
            "name":"ARA"            
           }
        """.trimIndent()
        val request = MockMvcRequestBuilders
            .put("/stores/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        val storeUpdate= entityManager.find(Store::class.java,16L)
        Assertions.assertEquals(200, resp.status)
        Assertions.assertEquals("C/15",store.address)
        Assertions.assertEquals("ARA",store.name)
    }

    @Test
    fun findAllStoresHappyPath(){
        val city = City(1,"Armenia")
        entityManager.persist(city)
        val storeOne = Store(1L,"C/10","Exito",city)
        entityManager.persist(storeOne)
        val storeTwo = Store(2L,"C/11","ARA",city)
        entityManager.persist(storeTwo)

        val request = MockMvcRequestBuilders.get("/stores")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  stores = objectMapper.readValue(json, Array<Store>::class.java)
        Assertions.assertEquals(2,stores.size)
        Assertions.assertEquals("C/10",stores[0].address)
        Assertions.assertEquals("Exito",stores[0].name)
        Assertions.assertEquals("C/11",stores[1].address)
        Assertions.assertEquals("ARA",stores[1].name)
    }

    @Test
    fun findProductsByStoreThisStoreDoesNotExist(){
        val request = MockMvcRequestBuilders.get("/stores/111/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This store does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }

    @Test
    fun findProductsByStoreHappyPath(){
        val category = Category(1,"Aseo")
        entityManager.persist(category)
        val productOne = Product("111","Jabon","Avon",category)
        entityManager.persist(productOne)
        val productTwo = Product("222","Shampóo","TioNacho",category)
        entityManager.persist(productTwo)
        val productThree = Product("333","PrestoBarba","Guillette",category)
        entityManager.persist(productThree)
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val storeOne = Store(1,"C/10","Exito",city)
        entityManager.persist(storeOne)
        val storeTwo= Store(2,"C/15","ARA",city)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(1,2000.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(2,2500.0,20.0,productOne,storeTwo))
        entityManager.persist(ProductStore(3,3000.0,25.0,productThree,storeTwo))
        val request = MockMvcRequestBuilders.get("/stores/3/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  productsByStoreTwo= objectMapper.readValue(json, Array<Product>::class.java)
        Assertions.assertEquals(1,productsByStoreTwo.size)
        Assertions.assertEquals("Jabon",productsByStoreTwo[0].name)
        Assertions.assertEquals("Avon",productsByStoreTwo[0].branch)
        Assertions.assertEquals("PrestoBarba",productsByStoreTwo[1].name)
        Assertions.assertEquals("Guillette",productsByStoreTwo[1].branch)
    }

    @Test
    fun findProductsByStoreAndByCategoryThisStoreDoesNotExist(){
        val request = MockMvcRequestBuilders.get("/stores/111/categories/1/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This store does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }

    @Test
    fun findProductsByStoreAndByCategoryThisCategoryDoesNotExist(){
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val store = Store(1,"C/10","Exito",city)
        entityManager.persist(store)
        val request = MockMvcRequestBuilders.get("/stores/1/categories/15/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isPreconditionFailed)

        val json = response.andReturn().response.contentAsString;
        val  error= objectMapper.readValue(json, ErrorResponse::class.java)
        Assertions.assertEquals("This category does not exist", error.message)
        Assertions.assertEquals(412, error.code)
    }

    @Test
    fun findProductsByStoreAndByCategoryHappyPath(){
        val categoryOne = Category(1,"Aseo")
        entityManager.persist(categoryOne)
        val categoryTwo = Category(2,"Comida")
        entityManager.persist(categoryTwo)
        val productOne = Product("111","Jabon","Avon",categoryOne)
        entityManager.persist(productOne)
        val productTwo = Product("222","DeTodito","Margarita",categoryTwo)
        entityManager.persist(productTwo)
        val productThree = Product("333","Ponke","Bimbo",categoryTwo)
        entityManager.persist(productThree)
        val city = City(1L,"Armenia")
        entityManager.persist(city)
        val store = Store(1,"C/10","Exito",city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(1,2000.0,15.0,productTwo,store))
        entityManager.persist(ProductStore(2,3000.0,20.0,productOne,store))
        entityManager.persist(ProductStore(3,5000.0,25.0,productThree,store))
        val request = MockMvcRequestBuilders.get("/stores/1/categories/2/products")
            .contentType(MediaType.APPLICATION_JSON)

        val response = mocMvc.perform(request)
        response.andExpect(MockMvcResultMatchers.status().isOk)

        val json = response.andReturn().response.contentAsString;

        val  productsByStoreAndByCategory= objectMapper.readValue(json, Array<Product>::class.java)
        Assertions.assertEquals(2,productsByStoreAndByCategory.size)
        Assertions.assertEquals("DeTodito",productsByStoreAndByCategory[0].name)
        Assertions.assertEquals("Margarita",productsByStoreAndByCategory[0].branch)
        Assertions.assertEquals("Ponke",productsByStoreAndByCategory[1].name)
        Assertions.assertEquals("Bimbo",productsByStoreAndByCategory[1].branch)
    }

}