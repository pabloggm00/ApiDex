package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Usuario

data class GetUsuarioDto(
    var id: Long?,
    var username: String,
    var email: String,
    var pass: String,
    var avatar: String
)

fun Usuario.toGetUsuairoDto(): GetUsuarioDto = GetUsuarioDto(id,username,email,pass,avatar)

data class GetUsuarioRegistradoDto(
    var id: Long?,
    var username: String,
    var email:String,
)

fun Usuario.toGetUsuarioRegistradoDto(): GetUsuarioRegistradoDto = GetUsuarioRegistradoDto(id, username,email)

data class GetLoginDto(
    var id: Long?,
    var username: String,
    var email: String
)

fun Usuario.toGetLoginDto(): GetLoginDto = GetLoginDto(id, username, email)

data class GetPerfilUsuarioDto(
    var username: String,
    var email: String,
    var avatar: String
)

fun Usuario.toGetPerfilUsuarioDto(): GetPerfilUsuarioDto = GetPerfilUsuarioDto(username, email)

data class UsuarioDto(
    var username: String,
    var email: String,
    var roles: String,
    var id: Long? = null
)

fun Usuario.toUsuarioDto() : UsuarioDto = UsuarioDto(username, email, roles.joinToString(), id)
