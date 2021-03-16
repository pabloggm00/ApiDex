package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.FavoriteNotFoundException
import com.salesianostriana.dam.ApiDex.error.ListEntityNotFoundException
import com.salesianostriana.dam.ApiDex.error.SingleEntityNotFoundException
import com.salesianostriana.dam.ApiDex.services.ImagenPokemonService
import com.salesianostriana.dam.ApiDex.services.PokemonService
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import com.salesianostriana.dam.ApiDex.upload.ImgurBadRequest
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @Autowired
    lateinit var pokemonService: PokemonService

    @Autowired
    lateinit var imagenService: ImagenPokemonService

    @Autowired
    lateinit var usuarioService: UsuarioService

    @GetMapping
    fun getAllPokemon(@AuthenticationPrincipal usuario: Usuario,
        @RequestParam(name="tipo", required = false, defaultValue = "Todos") tipo: String,
        @RequestParam(name="generacion", required = false, defaultValue = "Todas") generacion: String
    ) : List<GetPokemonPokedexDto>{

        return pokemonService.getPokemonFiltrados(tipo, generacion)?.map { it.toGetPokemonDto(usuario) }
            .takeIf { it!!.isNotEmpty() } ?: throw ListEntityNotFoundException(Pokemon::class.java)
    }

    @GetMapping("/{id}")
    fun getPokemonById(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long): GetPokemoDetalleDto {
        return pokemonService.findById(id)
            .map { it.toGetPokemonDetalleDto(usuario) }
            .orElseThrow {
                SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
            }
    }

    @PutMapping("/{id}")
    fun editPokemon(
        @AuthenticationPrincipal usuario: Usuario,
        @RequestBody editarPokemon: EditPokemonDto,
        @PathVariable id: Long
        ): GetPokemoDetalleDto {
        return pokemonService.findById(id)
            .map { pokemonEncontrado ->
                pokemonEncontrado.pC = editarPokemon.pC
                pokemonEncontrado.estrellas = editarPokemon.estrellas
                pokemonEncontrado.ataqueRapido = editarPokemon.ataqueRapido
                pokemonEncontrado.ataqueCargado = editarPokemon.ataqueCargado

                pokemonService.save(pokemonEncontrado).toGetPokemonDetalleDto(usuario)
            }
            .orElseThrow { SingleEntityNotFoundException(id.toString(), Pokemon::class.java) }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Any> {
        if (pokemonService.existsById(id))
            pokemonService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/img")
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
    }

    //Este método no estará en el final, solo es para testear
    @DeleteMapping("/{id}/img/{hash}")
    fun deleteImage(@PathVariable id: Long, @PathVariable hash: String): ResponseEntity<Any>{
        var pokemon: Pokemon = pokemonService.findById(id).orElse(null)

        if(pokemon != null){
            var image = imagenService.findByDeleteHash(hash)
            if (image != null) {
                imagenService.delete(image)

            }

        }
        return ResponseEntity.noContent().build()
    }

    /*POKEMON FAVORITOS*/

    @GetMapping("/favs")
    fun getPokemonFavs(@AuthenticationPrincipal usuario: Usuario): List<GetPokemonPokedexDto> {
        return pokemonService.getPokemonFavs(usuario)
            .map { it.toGetPokemonDto(usuario) }
            .takeIf { it.isNotEmpty() } ?: throw FavoriteNotFoundException(Pokemon::class.java)
    }

    @PostMapping("/favs/{id}")
    fun addPokemonFav(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {
        var pokemon = pokemonService.findById(id).orElse(null)

        if (pokemon != null){
            usuario.pokemonsFavs.add(pokemon)
            usuarioService.save(usuario)
            return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario))
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @DeleteMapping("/favs/{id}")
    fun deletePokemonFav(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long): ResponseEntity<Any>{
        usuario.pokemonsFavs.forEach { pokemon ->
            if (pokemon.id == id){
                usuario.pokemonsFavs.remove(pokemon)
                usuarioService.save(usuario)
            }
        }
        return ResponseEntity.noContent().build()
    }

    /*POKEMON CAPTURADOS*/

    @GetMapping("/capturados")
    fun getPokemonCapturados(@AuthenticationPrincipal usuario: Usuario): List<GetPokemonPokedexDto> {
        return pokemonService.getPokemonCapturados(usuario)
            .map { it.toGetPokemonDto(usuario) }
            .takeIf { it.isNotEmpty() } ?: throw FavoriteNotFoundException(Pokemon::class.java)
    }

    @PostMapping("/capturados/{id}")
    fun addPokemonCapturados(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {
        var pokemon = pokemonService.findById(id).orElse(null)

        if (pokemon != null){
            usuario.pokemonsCapturados.add(pokemon)
            usuarioService.save(usuario)
            return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario))
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @DeleteMapping("/capturados/{id}")
    fun deletePokemonCapturados(@AuthenticationPrincipal usuario: Usuario, @PathVariable id: Long): ResponseEntity<Any>{
        usuario.pokemonsCapturados.forEach { pokemon ->
            if (pokemon.id == id){
                usuario.pokemonsCapturados.remove(pokemon)
                usuarioService.save(usuario)
            }
        }
        return ResponseEntity.noContent().build()
    }


}