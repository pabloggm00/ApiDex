package com.salesianostriana.dam.ApiDex.entities.dto

data class GetImagenDto(
    var url: String
)

data class GetImagenDetalleDto(
    var id: Long?,
    var url: String,
    var deleteHash: String
)