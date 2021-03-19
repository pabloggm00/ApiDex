package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Tipo
import org.springframework.data.jpa.repository.JpaRepository

interface TipoRepository: JpaRepository<Tipo, Long> {
}