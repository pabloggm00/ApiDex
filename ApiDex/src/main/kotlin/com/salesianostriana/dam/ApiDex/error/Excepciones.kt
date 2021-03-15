package com.salesianostriana.dam.ApiDex.error


open class ExistsException(val msg: String) : RuntimeException(msg)


data class UsernameExistsException(
    val username: String
) : ExistsException("El username $username ya existe")