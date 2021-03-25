package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Equipo
import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.*
import com.salesianostriana.dam.ApiDex.services.EquipoService
import com.salesianostriana.dam.ApiDex.services.ImagenService
/*import com.salesianostriana.dam.ApiDex.services.ImagenPokemonService*/
import com.salesianostriana.dam.ApiDex.services.PokemonService
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import com.salesianostriana.dam.ApiDex.upload.ImgurBadRequest
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/pokemon")
class PokemonController {

    @Autowired
    lateinit var pokemonService: PokemonService

    @Autowired
    lateinit var imagenService: ImagenService

    @Autowired
    lateinit var usuarioService: UsuarioService


    @ApiOperation(value="Ver todos los Pokémon", notes = "El método devuelve todos los Pokémon en orden según el número de la Pokédex")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @GetMapping
    fun getAllPokemon(/*@AuthenticationPrincipal usuario: Usuario*/
        @RequestParam(name = "tipo", required = false, defaultValue = "todos") tipo: String,
        @RequestParam(name = "generacion", required = false, defaultValue = "todas") generacion: String
    ): List<GetPokemonPokedexDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)


        var listaPokemonFiltrados : List<Pokemon>? = pokemonService.getPokemonFiltrados(tipo, generacion)

        listaPokemonFiltrados = listaPokemonFiltrados?.sortedBy{ it.idPokedex.toInt() }

        return listaPokemonFiltrados?.map { it.toGetPokemonDto(usuario!!.get()) }
            .takeIf { it!!.isNotEmpty() } ?: throw ListEntityNotFoundException(Pokemon::class.java)

    }

    @ApiOperation("Ver un Pokémon según su id", notes = "Este método sirve para ver todo el detalle de un Pokémon")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @GetMapping("/{id}")
    fun getPokemonById(@PathVariable id: Long): GetPokemonDetalleDto {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.findById(id)
            .map { it.toGetPokemonDetalleDto(usuario!!.get()) }
            .orElseThrow {
                SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
            }

    }

    @ApiOperation("Duplicar un Pokémon", notes = "Este método hace que se cree " +
            "un Pokémon a partir del Pokémon original de la Pokédex. Solo se editan 4 atributos, los demás se quedarán por defecto")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PostMapping("/{id}")
    fun duplicate(
        @Valid
        @PathVariable id: Long,
        @RequestBody pokemonDuplicado: EditPokemonDto
    ): ResponseEntity<GetPokemonDetalleDto> {
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        var pokemon: Optional<Pokemon> = pokemonService.findById(id)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                pokemonService.save(
                    Pokemon(
                        pokemon.get().nombre,
                        pokemonDuplicado.estrellas ,
                        pokemonDuplicado.ataqueRapido,
                        pokemonDuplicado.ataqueCargado,
                        pokemonDuplicado.pC,
                        pokemon.get().idPokedex,
                        pokemon.get().isUltimo,
                        pokemon.get().isFav,
                        pokemon.get().isCapturado,
                        pokemonDuplicado.isOriginal,
                        pokemon.get().equipo,
                        pokemon.get().generacion,
                        pokemon.get().primerTipo,
                        pokemon.get().segundoTipo,
                        pokemon.get().imagen
                    )
                ).toGetPokemonDetalleDto(usuario!!.get())
            )
    }

    @ApiOperation("Editar un Pokémon", notes = "Con este método se puede editar únicamente 4 atributos del Pokémon.")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PutMapping("/{id}")
    fun editPokemon(
        @Valid
        @RequestBody editarPokemon: EditPokemonDto,
        @PathVariable id: Long
    ): GetPokemonDetalleDto {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        var pokemon: Pokemon = pokemonService.findById(id).orElse(null)

        if (!pokemon.isOriginal) {
            return pokemonService.findById(id)
                .map { pokemonEncontrado ->
                    pokemonEncontrado.pC = editarPokemon.pC
                    pokemonEncontrado.estrellas = editarPokemon.estrellas
                    pokemonEncontrado.ataqueRapido = editarPokemon.ataqueRapido
                    pokemonEncontrado.ataqueCargado = editarPokemon.ataqueCargado


                    pokemonService.save(pokemonEncontrado).toGetPokemonDetalleDto(usuario!!.get())
                }
                .orElseThrow { SingleEntityNotFoundException(id.toString(), Pokemon::class.java) }
        }else{
            throw PokemonOriginalNotFoundException(Pokemon::class.java)
        }
    }


    @ApiOperation("Borrar un Pokémon", notes = "Este método borra un Pokemon que existe. No puede eliminar un Pokémon no existente")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        var pokemon: Optional<Pokemon> = pokemonService.findById(id)
        if (pokemonService.existsById(id))
            if (!pokemon.get().isOriginal) {
                pokemonService.deleteById(id)
            }

        return ResponseEntity.noContent().build()
    }

    @ApiOperation("Evolucionar un Pokémon", notes = "Este método hace que suban los puntos de combates que son generados automáticamente," +
            "y cambia a su siguiente que es según el orden de la Pokédex. Un Pokémon original no puede evolucionar, y un Pokémon que ya está en su" +
            "última evolución, tampoco podrá")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PostMapping("/evolucion/{id}")
    fun evolucionar(@PathVariable id: Long) : ResponseEntity<GetPokemonDetalleDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        var pokemon: Optional<Pokemon> = pokemonService.findById(id)


        if (!pokemon.get().isUltimo){
            if (!pokemon.get().isOriginal){
                var pokemonEvolucionado = pokemonService.evolucionar(usuario!!.get(), id)
                pokemonService.deleteById(id)
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                        pokemonService.save(
                            Pokemon(
                                pokemonEvolucionado.nombre,
                                pokemon.get().estrellas,
                                pokemon.get().ataqueRapido,
                                pokemon.get().ataqueCargado,
                                pokemonEvolucionado.pC,
                                pokemonEvolucionado.idPokedex,
                                pokemonEvolucionado.isUltimo,
                                pokemon.get().isFav,
                                pokemon.get().isCapturado,
                                pokemon.get().isOriginal,
                                pokemon.get().equipo,
                                pokemon.get().generacion,
                                pokemon.get().primerTipo,
                                pokemon.get().segundoTipo,
                                pokemon.get().imagen
                            )
                        ).toGetPokemonDetalleDto(usuario!!.get()))
            }
        }
        throw EvolucionarOriginalNotFoundException(Pokemon::class.java)


    }


    @ApiOperation("Subir imagen Pokémon" , notes = "Este método no estará para el usuario, esto es para el creador de la base de datos, para que" +
            "pueda subir las imágenes de los Pokémon y asignarlos")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PostMapping("/{id}/img")
    fun createImage(
        /*@AuthenticationPrincipal usuario: Usuario,*/
        @PathVariable id:Long,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<GetPokemonDetalleDto>{

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        var pokemon: Pokemon = pokemonService.findById(id).orElse(null)


        if (pokemon != null){
            try {
                imagenService.saveImagenPokemon(file,pokemon)
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pokemon.toGetPokemonDetalleDto(usuario!!.get()))
            }catch (ex: ImgurBadRequest){
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Error en la subida de imagen")
            }
        } else {
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }


    @ApiOperation("Borrado de imagen del Pokémon", notes = "Este método no estará disponible para el usuario. Servirá como recurso para el creador" +
            "de la API para gestionar los Pokémon originales")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
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

    @ApiOperation("Ver Pokémon favoritos", notes = "Muestra la lista de todos los Pokémon favoritos del usuario")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @GetMapping("/favs")
    fun getPokemonFavs(): List<GetPokemonPokedexDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.getPokemonFavs(usuario!!.get())
            .map { it.toGetPokemonDto(usuario.get()) }
            .takeIf { it.isNotEmpty() } ?: throw FavoriteNotFoundException(Pokemon::class.java)
    }

    @ApiOperation("Añadir un Pokémon a favoritos", notes = "Este método se encarga de añadir el Pokémon elegido a la lista de favoritos")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PostMapping("/favs/{id}")
    fun addPokemonFav(@PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {

        var pokemon = pokemonService.findById(id).orElse(null)
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        if (pokemon != null){
            if(!usuario!!.get().pokemonsFavs.contains(pokemon)) {
                usuario.get().pokemonsFavs.add(pokemon)
                pokemon.isFav = true
                pokemonService.save(pokemon)
                usuarioService.save(usuario.get())
                return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario.get()))
            }else{
                throw PokemonFavoritoYaExiste(Pokemon::class.java)
            }
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @ApiOperation("Borrar un Pokémon de favoritos", notes = "Este método hace que un Pokémon se borre de la lista de favoritos")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @DeleteMapping("/favs/{id}")
    fun deletePokemonFav(@PathVariable id: Long): ResponseEntity<Any>{

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        lateinit var pokemon: Pokemon

        usuario!!.get().pokemonsFavs.forEach { pokemonEncontrado ->
            if (pokemonEncontrado.id == id){
               pokemon = pokemonEncontrado
            }
        }
        usuario.get().pokemonsFavs.remove(pokemon)
        pokemon.isFav = false
        pokemonService.save(pokemon)
        usuarioService.save(usuario.get())
        return ResponseEntity.noContent().build()
    }

    /*POKEMON CAPTURADOS*/

    @ApiOperation("Ver los Pokémon capturados", notes = "Recoge todos los Pokémon de la lista de capturados y los muestra")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @GetMapping("/capturados")
    fun getPokemonCapturados(): List<GetPokemonPokedexDto> {

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        return pokemonService.getPokemonCapturados(usuario!!.get())
            .map { it.toGetPokemonDto(usuario.get()) }
            .takeIf { it.isNotEmpty() } ?: throw CapturadoNotFoundException(Pokemon::class.java)
    }

    @ApiOperation("Añadir Pokémon a capturado", notes = "Esté método se encarga de añadir un Pokémon a la lista de capturados")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @PostMapping("/capturados/{id}")
    fun addPokemonCapturados(@PathVariable id: Long): ResponseEntity<GetPokemonPokedexDto> {

        var pokemon = pokemonService.findById(id).orElse(null)
        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        if (pokemon != null){
            if(!usuario!!.get().pokemonsCapturados.contains(pokemon)) {
                usuario.get().pokemonsCapturados.add(pokemon)
                pokemon.isCapturado = true
                usuarioService.save(usuario.get())
                pokemonService.save(pokemon)
                return ResponseEntity.status(HttpStatus.CREATED).body(pokemon.toGetPokemonDto(usuario.get()))
            }else{
                throw PokemonCapturadoYaExiste(Pokemon::class.java)
            }
        }else{
            throw SingleEntityNotFoundException(id.toString(), Pokemon::class.java)
        }
    }

    @ApiOperation("Eliminar Pokémon capturado", notes = "Se encarga de eliminar dicho Pokémon de la lista de capturados")
    @ApiResponses(
        ApiResponse(code = 200, message = "Ok."),
        ApiResponse(code = 401, message = "No está autorizado"),
        ApiResponse(code = 500, message = "Error inesperado")
    )
    @DeleteMapping("/capturados/{id}")
    fun deletePokemonCapturados(@PathVariable id: Long): ResponseEntity<Any>{

        var auth: String = SecurityContextHolder.getContext().authentication.name
        var usuario: Optional<Usuario>? = usuarioService.findByUsername(auth)

        lateinit var pokemon: Pokemon

        usuario!!.get().pokemonsCapturados.forEach { pokemonEncontrado ->
            if (pokemonEncontrado.id == id){
                pokemon = pokemonEncontrado
            }
        }
        usuario.get().pokemonsCapturados.remove(pokemon)
        pokemon.isCapturado = false
        pokemonService.save(pokemon)
        usuarioService.save(usuario.get())

        return ResponseEntity.noContent().build()
    }




}