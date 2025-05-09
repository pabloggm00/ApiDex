package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.request.PokemonRequest
import com.salesianostriana.apidexandroid.data.poko.response.DetallePokemon
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import retrofit2.Call
import retrofit2.http.*

interface PokemonService {

    @GET("pokemon")
    fun getPokemonList(@Header("Authorization") token: String?,
                       @Query("tipo") paramTipo: String?,
                       @Query("generacion") paramGeneracion: String?) : Call<List<Pokemon>>

    //Habrá que poner un filtro también en la lista de Pokemon favoritos en la API
    @GET("pokemon/favs")
    fun getPokemonFavs(@Header("Authorization") token: String?) : Call<List<Pokemon>>

    @GET("pokemon/capturados")
    fun getPokemonCapturados(@Header("Authorization") token: String?) : Call<List<Pokemon>>

    @POST("pokemon/favs/{id}")
    fun addPokemonFav(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<Pokemon>

    @POST("pokemon/capturados/{id}")
    fun addPokemonCapturado(@Header("Authorization") token: String?, @Path("id") idPokemon: Long): Call<Pokemon>

    @DELETE("pokemon/capturados/{id}")
    fun deleteCapturadoPokemon(@Header("Authorization") token: String?, @Path("id") idPokemon: Long): Call<Any>

    @DELETE("pokemon/favs/{id}")
    fun deleteFavPokemon(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<Any>

    @GET("pokemon/{id}")
    fun getDetallePokemon(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<DetallePokemon>

    @POST("pokemon/{id}")
    fun duplicarPokemon(@Header("Authorization") token: String?, @Body nuevoPokemon: PokemonRequest, @Path("id") idPokemon: Long) : Call<DetallePokemon>

    @DELETE("pokemon/{id}")
    fun deletePokemon(@Header("Authorization") token: String?,  @Path("id") idPokemon: Long): Call<Any>

    @PUT("pokemon/{id}")
    fun editarPokemon(@Header("Authorization") token: String?, @Body editPokemon: PokemonRequest, @Path("id") idPokemon: Long) : Call<DetallePokemon>

    @POST("pokemon/evolucion/{id}")
    fun evolucionarPokemon(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<DetallePokemon>

}