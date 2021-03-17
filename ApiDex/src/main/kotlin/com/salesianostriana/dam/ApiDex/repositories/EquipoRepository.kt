package com.salesianostriana.dam.ApiDex.repositories

import com.salesianostriana.dam.ApiDex.entities.Equipo
import org.springframework.data.jpa.repository.JpaRepository

interface EquipoRepository: JpaRepository<Equipo, Long> {
}