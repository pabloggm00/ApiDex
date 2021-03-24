package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Tipo
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.GetPokemonDetalleDto

import com.salesianostriana.dam.ApiDex.repositories.PokemonRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import org.springframework.stereotype.Service
import java.util.*

@Service
class PokemonService: BaseServiceImpl<Pokemon, Long, PokemonRepository>() {

    fun getPokemonFiltrados(tipo: String, generacion: String): List<Pokemon>? {
        var lista: List<Pokemon>? = repositorio?.findAll()

        if(tipo != "todos"){
            lista = lista?.filter { it.primerTipo.nombreTipo.toLowerCase() == tipo.toLowerCase() || it.segundoTipo?.nombreTipo?.toLowerCase() == tipo.toLowerCase() }
        }
        if (generacion != "todas"){
            lista = lista?.filter { it.generacion?.nombre?.toLowerCase() == generacion.toLowerCase() }
        }
        return lista
    }


    fun getPokemonFavs (usuario: Usuario) : List<Pokemon> = usuario.pokemonsFavs

    fun getPokemonCapturados (usuario: Usuario) : List<Pokemon> = usuario.pokemonsCapturados

    fun evolucionar(usuario: Usuario, id: Long): Pokemon {

        var pokemonAEvolucionar : Optional<Pokemon> = repositorio!!.findById(id)

        var nombre: String = pokemonAEvolucionar.get().nombre

        var listaPokemon: List<Pokemon> = repositorio!!.findAll()

        lateinit var pokemonAEvolucionarOriginal: Pokemon

        for(pokemon in listaPokemon){
            if (pokemon.nombre == nombre && pokemon.isOriginal){
                pokemonAEvolucionarOriginal = pokemon
            }
        }

        var pokemonEvolucionado : Optional<Pokemon> = repositorio!!.findById(pokemonAEvolucionarOriginal.id!!.plus(1))

        //var pokemonEvolucionado : Optional<Pokemon> = repositorio!!.findById(id.plus(1))

        fun Random.nextInt(range: IntRange): Int {
            return range.start + nextInt(range.last - range.start)
        }

        val random = Random()

        pokemonAEvolucionar.get().idPokedex = pokemonEvolucionado.get().idPokedex
        pokemonAEvolucionar.get().pC = pokemonAEvolucionar.get().pC!!.plus(random.nextInt(300..800))
        pokemonAEvolucionar.get().nombre = pokemonEvolucionado.get().nombre
        pokemonAEvolucionar.get().imagen = pokemonEvolucionado.get().imagen

        if (pokemonEvolucionado.get().isUltimo){
            pokemonAEvolucionar.get().isUltimo = true
        }

        return pokemonAEvolucionar.get()

    }
}