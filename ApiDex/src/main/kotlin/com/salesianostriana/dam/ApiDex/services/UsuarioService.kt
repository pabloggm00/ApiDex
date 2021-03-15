package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.EditUsuarioDto
import com.salesianostriana.dam.ApiDex.repositories.UsuarioRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(private val encoder: PasswordEncoder): BaseServiceImpl<Usuario, Long, UsuarioRepository>() {

    fun create(newUser: EditUsuarioDto): Optional<Usuario> {
        if(findByUsername(newUser.username)!!.isPresent)
            return Optional.empty()
        return Optional.of(
            with(newUser) {
                repositorio!!.save(
                    Usuario(email,  username, encoder.encode(pass), /*"https://robohash.org/${username}",*/"USER")
                )
            }
        )
    }

    fun findByUsername(username: String) = repositorio?.findByUsername(username)
}