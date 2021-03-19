package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Equipo
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.repositories.EquipoRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
class EquipoService: BaseServiceImpl<Equipo, Long, EquipoRepository>() {

    fun getEquipos(usuario: Usuario) = usuario.listaEquipos
}