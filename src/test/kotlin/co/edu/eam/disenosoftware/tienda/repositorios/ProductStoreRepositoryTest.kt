package co.edu.eam.disenosoftware.tienda.repositorios


import co.edu.eam.disenosoftware.tienda.modelos.Entities.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional

class ProductStoreRepositoryTest {
    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreateProductStore() {
        val category = Category(1,"categoria_uno")
        entityManager.persist(category)

        val product = Product(1,"jugo","nestle",category)
        entityManager.persist(product)

        val city = City(1,"Armenia")
        entityManager.persist(city)

        val store = Store("1","C/Juan n 5","Waxis",city)
        entityManager.persist(store)

        productStoreRepository.create(ProductStore(1,12000.00,10.00,product,store))

        val productStore = entityManager.find(ProductStore::class.java,  1)
        Assertions.assertNotNull(productStore)
        Assertions.assertEquals(1, productStore.id)
        Assertions.assertEquals(12000.00, productStore.price)
        Assertions.assertEquals(10.00, productStore.stock)
        Assertions.assertEquals("jugo", productStore.product.name)
        Assertions.assertEquals("Waxis", productStore.store.name)
    }

    @Test
    fun testDelete(){

        val category = Category(1,"categoria_uno")
        entityManager.persist(category)

        val product = Product(1,"jugo","nestle",category)
        entityManager.persist(product)

        val city = City(1,"Armenia")
        entityManager.persist(city)

        val store = Store("1","C/Juan n 5","Waxis",city)
        entityManager.persist(store)

        entityManager.persist(ProductStore(1,12000.00,10.00,product,store))

        productStoreRepository.delete(1)

        val productStore = entityManager.find(ProductStore::class.java, 1)
        Assertions.assertNull(productStore)
    }

    @Test
    fun findTest() {
        val category = Category(1,"categoria_uno")
        entityManager.persist(category)

        val product = Product(1,"jugo","nestle",category)
        entityManager.persist(product)

        val city = City(1,"Armenia")
        entityManager.persist(city)

        val store = Store("1","C/Juan n 5","Waxis",city)
        entityManager.persist(store)

        entityManager.persist(ProductStore(1,12000.00,10.00,product,store))

        val productStore = productStoreRepository.find(1)

        Assertions.assertNotNull(productStore)
        Assertions.assertEquals("Waxis", productStore?.store?.name)
        Assertions.assertEquals("jugo", productStore?.product?.name)
    }

    @Test
    fun testUpdate() {
        val category = Category(1,"categoria_uno")
        entityManager.persist(category)

        val product = Product(1,"jugo","nestle",category)
        entityManager.persist(product)

        val city = City(1,"Armenia")
        entityManager.persist(city)

        val store = Store("1","C/Juan n 5","Waxis",city)
        entityManager.persist(store)

        entityManager.persist(ProductStore(1,12000.00,10.00,product,store))

        entityManager.flush()

        val productStore = entityManager.find(ProductStore::class.java, 1)
        productStore.price = 20000.00
        productStore.stock = 20.00
        productStore.store.name = "Waglio"
        productStore.product.name = "Leche"

        entityManager.clear()

        productStoreRepository.update(productStore)

        val productStoreToAssert = entityManager.find(ProductStore::class.java, 1)
        Assertions.assertEquals(20000.00, productStoreToAssert.price)
        Assertions.assertEquals(20.00, productStoreToAssert.stock)
        Assertions.assertEquals("Waglio", productStoreToAssert.store.name)
        Assertions.assertEquals("Leche", productStoreToAssert.product.name)
    }

    @Test
    fun findByStoreTest(){
        val category = Category(1L,"categoria_uno")
        val productOne = Product(1,"Jugo","Nestle",category)
        val productTwo = Product(2,"cuido","Ringo",category)
        val productThree = Product(3,"papitas","Margarita",category)
        val city = City(1L,"Armenia")
        val storeOne = Store("1","Cra 15","Tienda mascotas",city)
        val storeTwo = Store("2","Cra 17","Tienda helados",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        entityManager.persist(ProductStore(5L,50000.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(6L,50000.0,15.0,productThree,storeTwo))

        val listOne = productStoreRepository.findByStore("1")
        val listTwo = productStoreRepository.findByStore("2")

        Assertions.assertEquals(2,listOne.size)
        Assertions.assertEquals(1,listTwo.size)
    }

    @Test
    fun findByStoreAndByCategoryTest(){
        val categoryOne = Category(1L,"categoria_uno")
        val categoryTwo= Category(2L,"categoria_dos")
        val productOne = Product(1,"Jugo","Nestle",categoryOne)
        val productTwo = Product(2,"cuido","Ringo",categoryOne)
        val productThree = Product(3,"papitas","Margarita",categoryTwo)
        val city = City(15L,"Armenia")
        val storeOne = Store("1","Cra 15","Tienda mascotas",city)
        val storeTwo = Store("2","Cra 17","Tienda helados",city)
        entityManager.persist(categoryOne)
        entityManager.persist(categoryTwo)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(storeTwo)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        entityManager.persist(ProductStore(5L,50000.0,15.0,productTwo,storeOne))
        entityManager.persist(ProductStore(6L,50000.0,15.0,productThree,storeTwo))

        val listOne=productStoreRepository.findByStoreAndByCategory(1L,1L)
        val listTwo= productStoreRepository.findByStoreAndByCategory(12L,2L)

        Assertions.assertEquals(2,listOne.size)
        Assertions.assertEquals(1,listTwo.size)
    }
}