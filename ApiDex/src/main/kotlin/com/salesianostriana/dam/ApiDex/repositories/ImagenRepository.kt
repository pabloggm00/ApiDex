package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Imagen
import org.springframework.data.jpa.repository.JpaRepository

interface ImagenRepository: JpaRepository<Imagen, Long> {

    abstract fun findByDeleteHash(deleteHash: String): Imagen
}