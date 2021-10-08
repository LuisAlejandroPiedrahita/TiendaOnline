package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.excetions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.User
import co.edu.eam.disenosoftware.tienda.repositorios.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class UserServicios {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createUser(user: User){
        val userById = userRepository.find(user.id)

        if(userById != null){
            throw BusinessException("This User already exists")
        }
        userRepository.create(user)
    }

    fun editUser(user: User) {
        val userById = userRepository.find(user.id)

        if (userById == null) {
            throw BusinessException("This User does not exist")
        }

        userRepository.update(user)
    }
}