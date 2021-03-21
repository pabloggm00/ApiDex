package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.SingleEntityNotFoundException
import com.salesianostriana.dam.ApiDex.services.ImagenService
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import com.salesianostriana.dam.ApiDex.upload.ImgurBadRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/user")
class UsuarioController {

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var imagenService: ImagenService

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    fun me(@AuthenticationPrincipal usuario : Usuario) = usuario.toGetUsuarioDto()

    @PutMapping("/{id}")
    fun editUser(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long, @RequestBody editUsuario: EditPerfilDto) : Optional<GetUsuarioDto>? {

        return usuarioService.findById(id)
            .map { usuario ->
                usuario.email = editUsuario.email
                usuarioService.save(usuario).toGetUsuarioDto()
            }
            //.takeIf { SingleEntityNotFoundException(id.toString(), Usuario::class.java) }
    }


    @PostMapping("/img")
    fun createImage(
        /*@AuthenticationPrincipal usuario: Usuario,*/
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<GetUsuarioDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        /*var user: Usuario = usuarioService.findById(id).orElse(null)*/


        if (usuario != null){
            try {
                imagenService.saveImagenUsuario(file,usuario.get())
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(usuario.get().toGetUsuarioDto())
            }catch (ex: ImgurBadRequest){
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de imagen")
            }
        } else {
            throw SingleEntityNotFoundException(usuario?.get()?.id.toString(), Pokemon::class.java)
        }
    }

}