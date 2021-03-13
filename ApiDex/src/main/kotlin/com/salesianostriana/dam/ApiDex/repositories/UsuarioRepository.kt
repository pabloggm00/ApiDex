package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario,Long> {
}