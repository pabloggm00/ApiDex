package com.salesianostriana.apidexandroid.ui.nuevoPokemon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.salesianostriana.apidexandroid.MainActivity
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.request.PokemonRequest
import com.salesianostriana.apidexandroid.data.poko.response.DetallePokemon
import com.salesianostriana.apidexandroid.retrofit.PokemonService
import com.salesianostriana.apidexandroid.ui.detallePokemon.DetallePokemonActivity
import com.salesianostriana.apidexandroid.ui.pokedex.PokedexViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NuevoPokemonActivity : AppCompatActivity() {

    var baseUrl = "http://10.0.2.2:9000/"

    lateinit var retrofit: Retrofit
    lateinit var service: PokemonService
    val context = this

    lateinit var editPC : EditText
    lateinit var editValoracion: EditText
    lateinit var editAtaqueRapido: EditText
    lateinit var editAtaqueCargado: EditText
    lateinit var btnCrear: Button
    lateinit var token: String
    var _pokemonId: Long? = 0
    var editar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_pokemon)

        editar = intent.getBooleanExtra("editar", false)
        _pokemonId = intent.extras?.getLong("idPokemon")

        editPC= findViewById(R.id.editText_PC)
        editValoracion = findViewById(R.id.editText_valoracion)
        editAtaqueRapido = findViewById(R.id.editText_ataqueRapido)
        editAtaqueCargado = findViewById(R.id.editText_ataqueCargado)
        btnCrear = findViewById(R.id.btn_crear)

        if(editar) {
            cambiarTitulo("Editar Pokemon")
        }else{
            cambiarTitulo("Nuevo Pokemon")
        }

        val sharedPref = context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "").toString()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)

        btnCrear.setOnClickListener(View.OnClickListener {
            doCreate()
        })

    }

    fun cambiarTitulo(titulo: String?) {
        title = titulo
    }

    fun doCreate(){

        val pokemonData = PokemonRequest(
                editValoracion.text.toString().toInt(),
                editAtaqueRapido.text.toString(),
                editAtaqueCargado.text.toString(),
                editPC.text.toString().toInt(),
                false
        )

        if (!editar) {
            service.duplicarPokemon("Bearer ${token}", pokemonData, _pokemonId!!)
                .enqueue(object : Callback<DetallePokemon> {
                    override fun onResponse(
                        call: Call<DetallePokemon>,
                        response: Response<DetallePokemon>
                    ) {

                        if (response.code() == 201) {
                            Toast.makeText(context, "Duplicado correctamente", Toast.LENGTH_SHORT)
                                .show()
                            /*var intent = Intent(context, DetallePokemonActivity::class.java).apply {
                            putExtra("pokemonId", pokemonData.)
                        }*/
                            var intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            Log.i("code", response.code().toString())
                            Toast.makeText(context, "No se ha podido duplicar", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<DetallePokemon>, t: Throwable) {
                        Log.e("Error!!!", t.message.toString())
                    }

                })
        }else{
            service.editarPokemon("Bearer ${token}", pokemonData, _pokemonId!!)
                .enqueue(object : Callback<DetallePokemon> {
                    override fun onResponse(
                        call: Call<DetallePokemon>,
                        response: Response<DetallePokemon>
                    ) {

                        if (response.code() == 200) {
                            Toast.makeText(context, "Editado correctamente", Toast.LENGTH_SHORT)
                                .show()
                            /*var intent = Intent(context, DetallePokemonActivity::class.java).apply {
                            putExtra("pokemonId", pokemonData.)
                        }*/
                            var intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        } else {
                            Log.i("code", response.code().toString())
                            Toast.makeText(context, "No se ha podido editar", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<DetallePokemon>, t: Throwable) {
                        Log.e("Error!!!", t.message.toString())
                    }

                })
        }
    }
}