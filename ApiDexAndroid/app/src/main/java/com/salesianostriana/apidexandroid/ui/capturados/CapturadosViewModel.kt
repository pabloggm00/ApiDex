package com.salesianostriana.apidexandroid.ui.capturados

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import com.salesianostriana.apidexandroid.retrofit.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CapturadosViewModel(application: Application) : AndroidViewModel(application) {

    private val _pokemons = MutableLiveData<List<Pokemon>>()

    private val baseUrl = "http://10.0.2.2:9000/"

    private var service: PokemonService
    var token: String?


    private val context = getApplication<Application>().applicationContext

    val pokemon: LiveData<List<Pokemon>>
        get() = _pokemons

    init {
        _pokemons.value = listOf()

        val sharedPref = context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")

        var retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)

        getPokemonCapturados()

    }

    fun getPokemonCapturados() {
        service.getPokemonCapturados("Bearer ${token}").enqueue(object : Callback<List<Pokemon>> {
            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.code() == 200) {
                    _pokemons.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Toast.makeText(context, "No se ha encontrado ningún Pokemon capturado", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun addPokemonFav(pokemonId: Long, isFav: Boolean) {

        if (!isFav) {
            service.addPokemonFav("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 201) {

                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }


            })
        } else {
            service.deleteFavPokemon("Bearer ${token}", pokemonId).enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.code() == 204) {
                        getPokemonCapturados()
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
                        getPokemonCapturados()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }
}