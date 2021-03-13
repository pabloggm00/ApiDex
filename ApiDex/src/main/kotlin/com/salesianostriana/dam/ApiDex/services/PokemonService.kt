package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.repositories.PokemonRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
class PokemonService: BaseServiceImpl<Pokemon, Long, PokemonRepository>() {

    fun getPokemonFiltrados(primerTipo: String, generacion: String): List<Pokemon>? {
        var lista: List<Pokemon>? = repositorio?.findAll()
        if(primerTipo != "todos"){
            lista = lista?.filter { it.primerTipo.nombreTipo.toLowerCase() == primerTipo.toLowerCase() }
        }
        if (generacion != "todas"){
            lista = lista?.filter { it.pokedex.nombre.toLowerCase() == generacion.toLowerCase() }
        }
        return lista
    }

    fun getPokemonFavs (usuario: Usuario) : List<Pokemon> = usuario.pokemonsFavs

    fun getPokemonCapturados (usuario: Usuario) : List<Pokemon> = usuario.pokemonsCapturados
}