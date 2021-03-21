package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Equipo
import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.EquipoNotFoundException
import com.salesianostriana.dam.ApiDex.error.PokemonOriginalNotFoundException
import com.salesianostriana.dam.ApiDex.error.SingleEntityNotFoundException
import com.salesianostriana.dam.ApiDex.services.EquipoService
import com.salesianostriana.dam.ApiDex.services.PokemonService
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/equipo")
class EquipoController {

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var equipoService: EquipoService

    @Autowired
    lateinit var pokemonService: PokemonService


    /*EQUIPO*/

    @GetMapping
    fun getAllEquipos(): List<GetEquipoDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return equipoService.getEquipos(usuario!!.get())
            .map { it.toGetEquipoDto() }
            .takeIf { it.isNotEmpty() } ?: throw EquipoNotFoundException(Equipo::class.java)

    }

    @GetMapping("/{id}")
    fun getEquipoById(@PathVariable id: Long): GetEquipoDetalleDto {

        /*var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)*/

        return equipoService.findById(id)
            .map { it.toGetEquipoDetalleDto() }
            .orElseThrow {
                SingleEntityNotFoundException(id.toString(), Equipo::class.java)
            }
    }

    @PostMapping("/{idEquipo}/pokemon/{idPokemon}")
    fun addPokemonEquipo(@PathVariable idEquipo: Long, @PathVariable idPokemon: Long) : ResponseEntity<GetEquipoDetalleDto> {
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        var pokemon : Pokemon = pokemonService.findById(idPokemon).orElse(null)
        var equipo: Equipo = equipoService.findById(idEquipo).orElse(null)

        if (usuario!!.isPresent) {
                if (!pokemon.isOriginal) {
                    equipo.listaPokemon.add(pokemon)

                    equipoService.save(equipo)
                    pokemonService.save(pokemon)

                    return ResponseEntity.status(HttpStatus.CREATED).body(equipo.toGetEquipoDetalleDto())
                } else {
                    throw PokemonOriginalNotFoundException(Pokemon::class.java)
                }
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(null)
        }
    }

    @PostMapping
    fun createEquipo(@RequestBody nuevoEquipo: EditEquipoDto): ResponseEntity<GetEquipoDetalleDto> {

        var auth : String = SecurityContextHolder.getContext().authentication.name
        var usuario : Optional<Usuario>? = usuarioService.findByUsername(auth)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                equipoService.save(
                    Equipo(
                        nuevoEquipo.nombre,
                        nuevoEquipo.liga,
                        usuario!!.get(),
                        nuevoEquipo.listaPokemons

                    )
                ).toGetEquipoDetalleDto())
    }

    @PutMapping("/{id}")
    fun editEquipo(@RequestBody editEquipo: EditEquipoDto, @PathVariable id: Long): GetEquipoDetalleDto {
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return equipoService.findById(id)
            .map { equipoEncontrado ->
                equipoEncontrado.liga = editEquipo.liga
                equipoEncontrado.nombre = editEquipo.nombre
                equipoEncontrado.listaPokemon = editEquipo.listaPokemons

                equipoService.save(equipoEncontrado).toGetEquipoDetalleDto()
            }
            .orElseThrow { SingleEntityNotFoundException(id.toString(), Equipo::class.java) }
    }

    @DeleteMapping("/{id}")
    fun deleteEquipo(@PathVariable id: Long): ResponseEntity<Any> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        usuario!!.get().listaEquipos.forEach { equipo ->
            if (equipo.id == id){
                usuario!!.get().listaEquipos.remove(equipo)
                usuarioService.save(usuario!!.get())
            }
        }
        return ResponseEntity.noContent().build()
    }
}