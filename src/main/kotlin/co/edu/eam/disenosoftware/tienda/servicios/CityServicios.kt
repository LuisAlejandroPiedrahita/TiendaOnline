package co.edu.eam.disenosoftware.tienda.servicios

import co.edu.eam.disenosoftware.tienda.exceptions.BusinessException
import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import co.edu.eam.disenosoftware.tienda.repositorios.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityServicios {

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createCity(city: City){
        val cityById = cityRepository.find(city.id)

        if(cityById != null){
            throw BusinessException("This city already exist")
        }
        cityRepository.create(city)
    }
}