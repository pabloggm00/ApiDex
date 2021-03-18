package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UsuarioRepository: JpaRepository<Usuario,Long> {

    fun findByUsername(username: String) : Optional<Usuario>
}