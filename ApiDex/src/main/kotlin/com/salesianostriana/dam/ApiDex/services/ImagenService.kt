package com.salesianostriana.dam.ApiDex.services

import com.salesianostriana.dam.ApiDex.entities.Imagen
import com.salesianostriana.dam.ApiDex.entities.Pokemon
import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.repositories.ImagenRepository
import com.salesianostriana.dam.ApiDex.services.base.BaseServiceImpl
import com.salesianostriana.dam.ApiDex.upload.ImgurStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImagenService(
    private val imageStorageService: ImgurStorageService
): BaseServiceImpl<Imagen, Long, ImagenRepository>() {

    val logger: Logger = LoggerFactory.getLogger(ImagenService::class.java)


    fun saveImagenPokemon(file: MultipartFile, pokemon: Pokemon) : Imagen {
        var imagen: Imagen
        var image : Optional<Imagen> = Optional.empty()
        if (!file.isEmpty) {
            image = imageStorageService.store(file)
        }

        imagen = image.orElse(null)
        imagen.pokemon = pokemon
        pokemon.imagen = imagen
        return save(imagen)
    }

    fun saveImagenUsuario(file: MultipartFile, usuario: Usuario) : Imagen {
        var imagen: Imagen
        var image : Optional<Imagen> = Optional.empty()
        if (!file.isEmpty) {
            image = imageStorageService.store(file)
        }

        imagen = image.orElse(null)
        imagen.usuario = usuario
        usuario.avatar = imagen
        return save(imagen)
    }

    override fun delete(i: Imagen) {
        logger.debug("Eliminando la entidad $i")
        i?.let { it.deleteHash?.let { it1 -> imageStorageService.delete(it1) } }
        super.delete(i)

    }

    fun findByDeleteHash(deleteHash: String) = repositorio?.findByDeleteHash(deleteHash)
}
