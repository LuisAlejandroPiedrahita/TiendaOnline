package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.User
import co.edu.eam.disenosoftware.tienda.repositorios.CityRepository
import co.edu.eam.disenosoftware.tienda.repositorios.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServicios {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createUser(user: User, idCity: Long){

        val city = cityRepository.find(idCity)

        if(city == null){
            throw BusinessException("This city does not exist")
        }

        val userById = userRepository.find(user.id)

        if(userById != null){
            throw BusinessException("This User already exists")
        }

        user.city = city
        userRepository.create(user)
    }

    fun editUser(user: User, idUser:String) {
        val userById = userRepository.find(idUser)

        if (userById == null) {
            throw BusinessException("This User does not exist")
        }

        user.city = userById.city
        userRepository.update(user)
    }
}