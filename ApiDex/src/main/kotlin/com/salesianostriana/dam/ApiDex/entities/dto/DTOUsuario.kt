package com.salesianostriana.dam.ApiDex.entities.dto

import com.salesianostriana.dam.ApiDex.entities.Usuario
import io.swagger.annotations.ApiModelProperty
import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class EditUsuarioDto(

    @ApiModelProperty(
        example = "pablo@email.com",
        dataType="String",
        value = "Email"
    )
    @get:Email(message = "{usuario.email.blank}")
    @get:NotBlank(message = "{usuario.email.blank}")
    @Column(unique = true)
    var email: String,

    @ApiModelProperty(
        example = "pablogonzalez",
        dataType="String",
        value = "Username"
    )
    @get:NotBlank(message = "{usuario.username.blank}")
    @get:Size( message = "{usuario.username.size}", min= 4, max= 20)
    @Column(unique = true)
    var username: String,

    @ApiModelProperty(
        example = "123456789",
        dataType="String",
        value = "Password"
    )
    @get:Size( message = "{usuario.password.size}", min= 8, max = 100)
    var pass: String,

    @ApiModelProperty(
        example = "USER",
        dataType="String",
        value = "Rol"
    )
    var roles: String?,

)

fun Usuario.editUsuarioDto(): EditUsuarioDto =
    EditUsuarioDto(email, username, pass, roles.joinToString())

data class EditPerfilDto(
    @ApiModelProperty(
        example = "pablogonzalez@email.com",
        dataType="String",
        value = "Email"
    )
    @get:Email(message = "{usuario.email.blank}")
    @get:NotBlank(message = "{usuario.email.blank}")
    @Column(unique = true)
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
    @ApiModelProperty(
        example = "pablogonzalez",
        dataType="String",
        value = "Username"
    )
    var username: String,
    @ApiModelProperty(
        example = "123456789",
        dataType="String",
        value = "Password"
    )
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

