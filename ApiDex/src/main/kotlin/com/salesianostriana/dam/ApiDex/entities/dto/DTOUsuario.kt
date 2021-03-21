package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Usuario
import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class EditUsuarioDto(

    @get:Email(message = "{usuario.email.blank}")
    var email: String,
    @get:NotBlank(message = "{usuario.username.blank}")
    @get:Size( message = "{usuario.username.size}", min= 4, max= 20)
    @Column(unique = true)
    var username: String,

    @get:Size( message = "{usuario.password.size}", min= 8, max = 100)
    var pass: String,
    //var avatar: String,
    var roles: String?,

)

fun Usuario.editUsuarioDto(): EditUsuarioDto =
    EditUsuarioDto(email, username, pass, roles.joinToString())

data class EditPerfilDto(
    var email: String
)

fun Usuario.toEditPerfilDto() : EditPerfilDto {

    var url: String = "http://10.0.2.2:9000/files/"


    return EditPerfilDto(
        email
    )
}

data class GetUsuarioDto(
    var id: Long?,
    var username: String,
    var email: String,
    var avatar: String
)

fun Usuario.toGetUsuarioDto(): GetUsuarioDto {

    var url: String = "http://10.0.2.2:9000/files/"

    if (avatar != null){
        return GetUsuarioDto(id,username,email,"${url}${avatar!!.dataId}")
    }else{
        return GetUsuarioDto(id, username, email, "https://robohash.org/${username}")
    }

}


data class GetUsuarioRegistradoDto(
    var id: Long?,
    var username: String,
    var email:String,
)

fun Usuario.toGetUsuarioRegistradoDto(): GetUsuarioRegistradoDto =
      GetUsuarioRegistradoDto(id, username, email)


data class GetLoginDto(
    var id: Long?,
    var username: String,
    var email: String
)

fun Usuario.toGetLoginDto(): GetLoginDto = GetLoginDto(id, username, email)

data class UsuarioDto(
    var username: String,
    var email: String,
    var roles: String,
    val id: Long? = null
)

fun Usuario.toUsuarioDto() : UsuarioDto = UsuarioDto(username, email, roles.joinToString(), id)

