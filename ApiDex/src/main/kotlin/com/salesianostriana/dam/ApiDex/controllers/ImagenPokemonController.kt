package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.services.ImagenPokemonService
import com.salesianostriana.dam.ApiDex.upload.ImgurImageNotFoundException
import com.salesianostriana.dam.ApiDex.upload.ImgurStorageService
import com.salesianostriana.dam.ApiDex.upload.MediaTypeUrlResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.jvm.Throws

@RestController
class ImagenPokemonController {

    @Autowired
    lateinit var servicio: ImagenPokemonService

    @Autowired
    lateinit var imgurStorageService: ImgurStorageService

    @Throws(ResponseStatusException::class)
    @GetMapping("/files/{id}")
    fun get(@PathVariable id: String) : ResponseEntity<Resource> {
        var resource: Optional<MediaTypeUrlResource>
        try {
            resource = imgurStorageService.loadAsResource(id)
            if (resource.isPresent) {
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.get().mediaType)).body(resource.get())
            }
            return ResponseEntity.noContent().build()
        } catch (ex: ImgurImageNotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada")
        }

    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        var imagen = servicio.findById(id).orElse(null)
        if(imagen != null){
            servicio.delete(imagen)

        }
        return ResponseEntity.noContent().build()
    }
}