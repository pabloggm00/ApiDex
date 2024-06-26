package com.salesianostriana.apidexandroid.ui.pokedex

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import com.salesianostriana.apidexandroid.retrofit.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokedexViewModel(application: Application) : AndroidViewModel(application){

    private val _pokemons = MutableLiveData<List<Pokemon>>()

    private val baseUrl = "http://10.0.2.2:9000/"

    private var service: PokemonService
    var token: String?
    var generacion: String?
    var tipo: String?

    private val context = getApplication<Application>().applicationContext

    val pokemon: LiveData<List<Pokemon>>
        get() = _pokemons



    init {
        _pokemons.value = listOf()

        val sharedPref = context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")
        generacion = sharedPref?.getString("generacion", "")
        tipo = sharedPref?.getString("tipo", "")

        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokemonService::class.java)

        getPokemonList()



    }

    fun getPokemonList() {
        service.getPokemonList("Bearer ${token}", tipo, generacion).enqueue(object : Callback<List<Pokemon>>{

            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.code() == 200){
                    _pokemons.value = response.body()

                }
            }


            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Toast.makeText(context, "No se ha encontrado ningun pokemon", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun addPokemonFav(pokemonId: Long, isFav: Boolean) {

        if (!isFav) {
            service.addPokemonFav("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 201){

                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }


            })
        }else{
            service.deleteFavPokemon("Bearer ${token}", pokemonId).enqueue(object : Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.code() == 204){

                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }

    fun addPokemonCapturado(pokemonId: Long, isCapturado: Boolean) {

        if (!isCapturado) {
            service.addPokemonCapturado("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 201) {

                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }


            })
        } else {
            service.deleteCapturadoPokemon("Bearer ${token}", pokemonId).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.code() == 204) {

                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }
}