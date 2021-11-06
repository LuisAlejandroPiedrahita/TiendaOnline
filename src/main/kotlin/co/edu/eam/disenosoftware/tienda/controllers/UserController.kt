package co.edu.eam.disenosoftware.tienda.controllers

import co.edu.eam.disenosoftware.tienda.modelos.Entities.User
import co.edu.eam.disenosoftware.tienda.servicios.UserServicios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userServicios: UserServicios

    @PostMapping("/cities/{id}")
    fun createUser(@PathVariable("id") idCity:Long, @RequestBody user: User){
        userServicios.createUser(user,idCity)
    }

    @PutMapping("{id}")
    fun editUser(@PathVariable("id") idUser:String,@RequestBody user: User){
        user.id= idUser
        userServicios.editUser(user,idUser)
    }
}