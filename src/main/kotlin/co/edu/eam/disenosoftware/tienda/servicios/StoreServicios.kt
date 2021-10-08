package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Store
import co.edu.eam.disenosoftware.tienda.modelos.User
import co.edu.eam.disenosoftware.tienda.repositorios.StoreRepository
import co.edu.eam.disenosoftware.tienda.repositorios.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class StoreServicios {
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createStore(store: Store){
        val styoreById = storeRepository.find(store.id)

        if(styoreById != null){
            throw BusinessException("This Store already exists")
        }
        storeRepository.create(store)
    }

    fun editStore(store: Store) {
        val styoreById = storeRepository.find(store.id)

        if (styoreById == null) {
            throw BusinessException("This Store does not exist")
        }

        storeRepository.update(store)
    }
}