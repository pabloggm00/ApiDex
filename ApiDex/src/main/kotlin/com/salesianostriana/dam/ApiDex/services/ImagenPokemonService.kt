package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.ImagenPokemon
import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.repositories.ImagenPokemonRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import com.salesianostriana.dam.ApiDex.upload.ImgurStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImagenPokemonService(
    private val imageStorageService: ImgurStorageService
): BaseServiceImpl<ImagenPokemon, Long, ImagenPokemonRepository>() {

    val logger: Logger = LoggerFactory.getLogger(ImagenPokemonService::class.java)


    fun save(file: MultipartFile, pokemon: Pokemon) : ImagenPokemon {
        var imagen: ImagenPokemon
        var image : Optional<ImagenPokemon> = Optional.empty()
        if (!file.isEmpty) {
            image = imageStorageService.store(file)
        }

        imagen = image.orElse(null)
        imagen.pokemon = pokemon
        return save(imagen)
    }

    override fun delete(i: ImagenPokemon) {
        logger.debug("Eliminando la entidad $i")
        i?.let { it.deleteHash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(i)

    }

    fun findByDeleteHash(deleteHash: String) = repositorio?.findByDeleteHash(deleteHash)
}