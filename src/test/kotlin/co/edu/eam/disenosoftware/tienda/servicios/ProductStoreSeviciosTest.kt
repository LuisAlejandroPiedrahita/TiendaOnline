package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class ProductStoreSeviciosTest {

    @Autowired
    lateinit var productStoreServicios: ProductStoreServicios

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductInStoreNotExistTest(){
        val category = Category(15L,"alimento")
        val productOne = Product("1","Detergente","Ariel",category)
        val productTwo = Product("2","cuido","Ringo",category)
        val productThree = Product("3","papitas","Margarita",category)
        val city = City(15L,"Armenia")
        val storeOne = Store(1L,"Cra 15","Tienda mascotas",city)

        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        val nuevoProducto = ProductStore(5L,50000.0,15.0,productOne,storeOne)

        try{
            productStoreServicios.createProductInStore(nuevoProducto)
            Assertions.fail()
        } catch (e: BusinessException){
            Assertions.assertEquals("This product already exists in this store", e.message)
        }
    }

    @Test
    fun createProductInStoreHappyPathTest(){
        val category = Category(15L,"alimento")
        val productOne = Product("1","Detergente","Ariel",category)
        val productTwo = Product("2","cuido","Ringo",category)
        val productThree = Product("3","papitas","Margarita",category)
        val city = City(15L,"Armenia")
        val storeOne = Store(1L,"Cra 15","Tienda mascotas",city)

        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(productTwo)
        entityManager.persist(productThree)
        entityManager.persist(city)
        entityManager.persist(storeOne)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,storeOne))
        val newProduct = ProductStore(5L,50000.0,15.0,productTwo,storeOne)
        productStoreServicios.createProductInStore(newProduct)

        val productStore= entityManager.find(ProductStore::class.java,5L)

        Assertions.assertEquals(50000.0,productStore.price)
        Assertions.assertEquals(15.0,productStore.stock)
        Assertions.assertEquals("cuido",productStore.product.name)
    }

    @Test
    fun decreaseStockProductStoreNotExistTest(){

        try{
            productStoreServicios.decreaseStock(4L,14.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This product store does not exist", e.message)
        }
    }

    @Test
    fun decreaseStockLessThanZeroTest(){
        val category = Category(15L,"alimento")
        val productOne = Product("1","Detergente","Ariel",category)
        val city = City(15L,"Armenia")
        val store = Store(1L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,store))

        try{
            productStoreServicios.decreaseStock(4L,16.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("there can be no less than zero stocks", e.message)
        }
    }

    @Test
    fun decreaseStockProductStoreHappyPathTest(){
        val category = Category(15L,"alimento")
        val productOne = Product("1","Detergente","Ariel",category)
        val city = City(15L,"Armenia")
        val store = Store(1L,"Cra 15","Tienda mascotas",city)
        entityManager.persist(category)
        entityManager.persist(productOne)
        entityManager.persist(city)
        entityManager.persist(store)
        entityManager.persist(ProductStore(4L,50000.0,15.0,productOne,store))
        productStoreServicios.decreaseStock(4L,14.0)

        val productStore= entityManager.find(ProductStore::class.java,4L)
        Assertions.assertEquals(1.0,productStore.stock)
    }

    @Test
    fun increaseStockProductStoreNotExistTest(){

        try{
            productStoreServicios.increaseStock(4L,14.0)
            Assertions.fail()
        }catch (e: BusinessException){
            Assertions.assertEquals("This product store does not exist", e.message)
        }
    }

}