package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Equipo
import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.*
import com.salesianostriana.dam.ApiDex.services.EquipoService
/*import com.salesianostriana.dam.ApiDex.services.ImagenPokemonService*/
import com.salesianostriana.dam.ApiDex.services.PokemonService
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @Autowired
    lateinit var pokemonService: PokemonService

    /*@Autowired
    lateinit var imagenService: ImagenPokemonService*/

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var equipoService: EquipoService

    @GetMapping
    fun getAllPokemon(/*@AuthenticationPrincipal usuario: Usuario*/
        @RequestParam(name="tipo", required = false, defaultValue = "todos") tipo: String,
        @RequestParam(name="generacion", required = false, defaultValue = "todas") generacion: String
    ) : List<GetPokemonPokedexDto>{

        var auth : String = SecurityContextHolder.getContext().authentication.name
        var usuario : Optional<Usuario>? = usuarioService.findByUsername(auth)


        return pokemonService.getPokemonFiltrados(tipo, generacion)?.map { it.toGetPokemonDto(usuario!!.get()) }
            .takeIf { it!!.isNotEmpty() } ?: throw ListEntityNotFoundException(Pokemon::class.java)

    }


    @GetMapping("/{id}")
    fun getPokemonById(@PathVariable id: Long): GetPokemoDetalleDto {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.findById(id)
            .map { it.toGetPokemonDetalleDto(usuario!!.get()) }
            .orElseThrow {
                SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
            }

    }

    @PutMapping("/{id}")
    fun editPokemon(
        @RequestBody editarPokemon: EditPokemonDto,
        @PathVariable id: Long
        ): GetPokemoDetalleDto {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.findById(id)
            .map { pokemonEncontrado ->
                pokemonEncontrado.pC = editarPokemon.pC
                pokemonEncontrado.estrellas = editarPokemon.estrellas
                pokemonEncontrado.ataqueRapido = editarPokemon.ataqueRapido
                pokemonEncontrado.ataqueCargado = editarPokemon.ataqueCargado


                pokemonService.save(pokemonEncontrado).toGetPokemonDetalleDto(usuario!!.get())
            }
            .orElseThrow { SingleEntityNotFoundException(id.toString(), Pokemon::class.java) }
    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Any> {
        if (pokemonService.existsById(id))
            pokemonService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    /*@PostMapping("/{id}/img")
    fun createImage(
        @AuthenticationPrincipal usuario: Usuario,
        @PathVariable id:Long,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<GetPokemoDetalleDto>{
        var pokemon: Pokemon = pokemonService.findById(id).orElse(null)

        if (pokemon != null){
            try {
                imagenService.save(file,pokemon)
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pokemon.toGetPokemonDetalleDto(usuario))
            }catch (ex: ImgurBadRequest){
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de imagen")
            }
        } else {
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }*/

    //Este método no estará en el final, solo es para testear
   /* @DeleteMapping("/{id}/img/{hash}")
    fun deleteImage(@PathVariable id: Long, @PathVariable hash: String): ResponseEntity<Any>{
        var pokemon: Pokemon = pokemonService.findById(id).orElse(null)

        if(pokemon != null){
            var image = imagenService.findByDeleteHash(hash)
            if (image != null) {
                imagenService.delete(image)

            }

        }
        return ResponseEntity.noContent().build()
    }*/

    /*POKEMON FAVORITOS*/

    @GetMapping("/favs")
    fun getPokemonFavs(): List<GetPokemonPokedexDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.getPokemonFavs(usuario!!.get())
            .map { it.toGetPokemonDto(usuario!!.get()) }
            .takeIf { it.isNotEmpty() } ?: throw FavoriteNotFoundException(Pokemon::class.java)
    }

    @PostMapping("/favs/{id}")
    fun addPokemonFav(@PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {

        var pokemon = pokemonService.findById(id).orElse(null)
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        if (pokemon != null){
            usuario!!.get().pokemonsFavs.add(pokemon)
            usuarioService.save(usuario!!.get())
            return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario!!.get()))
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @DeleteMapping("/favs/{id}")
    fun deletePokemonFav(@PathVariable id: Long): ResponseEntity<Any>{

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        usuario!!.get().pokemonsFavs.forEach { pokemon ->
            if (pokemon.id == id){
                usuario!!.get().pokemonsFavs.remove(pokemon)
                usuarioService.save(usuario!!.get())
            }
        }
        return ResponseEntity.noContent().build()
    }

    /*POKEMON CAPTURADOS*/

    @GetMapping("/capturados")
    fun getPokemonCapturados(): List<GetPokemonPokedexDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.getPokemonCapturados(usuario!!.get())
            .map { it.toGetPokemonDto(usuario!!.get()) }
            .takeIf { it.isNotEmpty() } ?: throw CapturadoNotFoundException(Pokemon::class.java)
    }

    @PostMapping("/capturados/{id}")
    fun addPokemonCapturados(@PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {

        var pokemon = pokemonService.findById(id).orElse(null)
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        if (pokemon != null){
            usuario!!.get().pokemonsCapturados.add(pokemon)
            usuarioService.save(usuario!!.get())
            return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario!!.get()))
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @DeleteMapping("/capturados/{id}")
    fun deletePokemonCapturados(@PathVariable id: Long): ResponseEntity<Any>{

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        usuario!!.get().pokemonsCapturados.forEach { pokemon ->
            if (pokemon.id == id){
                usuario!!.get().pokemonsCapturados.remove(pokemon)
                usuarioService.save(usuario!!.get())
            }
        }
        return ResponseEntity.noContent().build()
    }

    /*EQUIPO*/

    @GetMapping("/equipos")
    fun getAllEquipos(): List<GetEquipoDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return equipoService.getEquipos(usuario!!.get())
            .map { it.toGetEquipoDto() }
            .takeIf { it.isNotEmpty() } ?: throw EquipoNotFoundException(Equipo::class.java)

    }

    @GetMapping("/equipos/{id}")
    fun getEquipoById(@PathVariable id: Long): GetEquipoDetalleDto{

        /*var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)*/

        return equipoService.findById(id)
            .map { it.toGetEquipoDetalleDto() }
            .orElseThrow {
                SingleEntityNotFoundException(id.toString(), Equipo::class.java)
            }
    }

    @PostMapping("/equipos")
    fun createEquipo(@RequestBody nuevoEquipo: EditEquipoDto): ResponseEntity<GetEquipoDetalleDto>{

        var auth : String = SecurityContextHolder.getContext().authentication.name
        var usuario : Optional<Usuario>? = usuarioService.findByUsername(auth)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                equipoService.save(
                    Equipo(
                        nuevoEquipo.nombre,
                        nuevoEquipo.totalPC,
                        usuario!!.get(),
                        nuevoEquipo.liga,
                        nuevoEquipo.listaPokemon

                    )
                ).toGetEquipoDetalleDto())
    }

    @PutMapping("/equipos/{id}")
    fun editEquipo(@RequestBody editEquipo: EditEquipoDto, @PathVariable id: Long): GetEquipoDetalleDto{
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return equipoService.findById(id)
            .map { equipoEncontrado ->
                equipoEncontrado.liga = editEquipo.liga
                equipoEncontrado.nombre = editEquipo.nombre
                equipoEncontrado.listaPokemon = editEquipo.listaPokemon

                equipoService.save(equipoEncontrado).toGetEquipoDetalleDto()
            }
            .orElseThrow { SingleEntityNotFoundException(id.toString(), Equipo::class.java) }
    }

    @DeleteMapping("/{id}")
    fun deleteEquipo(@PathVariable id: Long): ResponseEntity<Any>{

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