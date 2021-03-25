package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.EditUsuarioDto
import com.salesianostriana.dam.ApiDex.entities.dto.GetPokemonDetalleDto
import com.salesianostriana.dam.ApiDex.repositories.UsuarioRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Esta clase contiene métodos del Usuario
 * @author Pablo González González
 *
 */

@Service
class UsuarioService(private val encoder: PasswordEncoder): BaseServiceImpl<Usuario, Long, UsuarioRepository>() {


    /**
     * Este método es para crear un usuario
     *
     * @param newUser Usuario que se quiere crear
     *
     * @return Devuelve el usuario creado
     */
    fun create(newUser: EditUsuarioDto): Optional<Usuario> {
        if(findByUsername(newUser.username)!!.isPresent)
            return Optional.empty()
        return Optional.of(
            with(newUser) {
                repositorio!!.save(
                    Usuario(email,  username, encoder.encode(pass),"USER")
                )
            }
        )
    }

    /**
     * Este método es para buscar al usuario por su nombre de usuario
     *
     * @param username Nombre de usuario
     *
     * @return Devuelve un Usuario
     */
    fun findByUsername(username: String) = repositorio?.findByUsername(username)


}