package co.edu.eam.disenosoftware.tienda.modelos.Request

data class ProductRequest(
    val id:Long,
    val price:Double,
    val stock:Double,
    val idProduct:String?,
    val idStore:Long?,
)
