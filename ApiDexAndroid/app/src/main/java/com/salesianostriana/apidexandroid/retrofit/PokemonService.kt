package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import retrofit2.Call
import retrofit2.http.*

interface PokemonService {

    @GET("pokemon")
    fun getPokemonList(@Header("Authorization") token: String?,
                       @Query("tipo") paramTipo: String?,
                       @Query("generacion") paramGeneracion: String?) : Call<List<Pokemon>>


    @POST("pokemon/favs/{id}")
    fun addPokemonFav(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<Pokemon>

    @DELETE("pokemon/favs/{id}")
    fun deleteFavPokemon(@Header("Authorization") token: String?, @Path("id") idPokemon: Long) : Call<Any>
}