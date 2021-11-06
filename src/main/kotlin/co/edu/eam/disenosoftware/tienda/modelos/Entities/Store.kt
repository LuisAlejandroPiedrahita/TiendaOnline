package co.edu.eam.disenosoftware.tienda.modelos.Entities

import co.edu.eam.disenosoftware.tienda.modelos.Entities.City
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_tienda")
data class Store(

    @Id
    @Column(name = "id_tienda")
    var id: Long?,

    @Column(name = "direccion")
    var address: String,

    @Column(name = "nombre")
    var name: String,

    @ManyToOne
    @JoinColumn(name="id_ciudad")
    var city: City?,

) : Serializable
