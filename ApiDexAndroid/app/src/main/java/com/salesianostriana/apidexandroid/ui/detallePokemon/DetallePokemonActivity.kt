package com.salesianostriana.apidexandroid.ui.detallePokemon

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.lifecycle.MutableLiveData
import coil.load
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.response.DetallePokemon
import com.salesianostriana.apidexandroid.retrofit.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallePokemonActivity : AppCompatActivity() {


    private var _detallePokemon = MutableLiveData<DetallePokemon>()
    private val baseUrl= "http://10.0.2.2:9000/"
    lateinit var service: PokemonService
    lateinit var retrofit: Retrofit
    var pokemonId: Long?= 0
    var token: String? = ""

    lateinit var idPokedexView: TextView
    lateinit var isFavView: ImageView
    lateinit var isCapView: ImageView
    lateinit var nombreView: TextView
    lateinit var fotoPokemon: ImageView
    lateinit var regionView: TextView
    lateinit var tipo1View: TextView
    lateinit var tipo2View: TextView
    lateinit var pCView: TextView
    lateinit var val1View: ImageView
    lateinit var val2View: ImageView
    lateinit var val3View: ImageView
    lateinit var ataqueRapidoView: TextView
    lateinit var ataqueCargadoView: TextView
    lateinit var btnDuplicar: Button
    lateinit var btnEvolucionar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pokemon)

        val sharedPref = this.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")

        pokemonId = intent.extras?.getLong("pokemonId")

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)



        idPokedexView = findViewById(R.id.textView_idPokedex)
        isFavView = findViewById(R.id.imageView_favorito)
        isCapView = findViewById(R.id.imageView_noCapturado)
        nombreView = findViewById(R.id.textView_nombrePokemon)
        fotoPokemon = findViewById(R.id.imageView_fotoPokemon)
        regionView = findViewById(R.id.textView_Region)
        tipo1View = findViewById(R.id.textView_tipo1)
        tipo2View = findViewById(R.id.textView_tipo2)
        pCView = findViewById(R.id.textView_PCnumber)
        val1View = findViewById(R.id.imageView_val1)
        val2View = findViewById(R.id.imageView_val2)
        val3View = findViewById(R.id.imageView_val3)
        ataqueRapidoView = findViewById(R.id.textView_ataqueR)
        ataqueCargadoView = findViewById(R.id.textView_ataqueC)
        btnDuplicar = findViewById(R.id.button_duplicar)
        btnEvolucionar = findViewById(R.id.button_evolucionar)
    }

    fun getDetallePokemon(){
        service.getDetallePokemon("Bearer ${token}", pokemonId!!).enqueue(object : Callback<DetallePokemon>{
            override fun onResponse(call: Call<DetallePokemon>, response: Response<DetallePokemon>) {
                if (response.code() == 200){
                    _detallePokemon.value = response.body()

                    idPokedexView.text = _detallePokemon.value?.idPokedex
                    if (_detallePokemon.value!!.isFav)
                        isFavView.load(R.drawable.ic_isfav)
                    else
                        isFavView.load(R.drawable.ic_nofav)

                    if (_detallePokemon.value!!.isCapturado)
                        isCapView.load(R.drawable.ic_capturado)
                    else
                        isCapView.load(R.drawable.ic_nocapturado)

                    nombreView.text = _detallePokemon.value?.nombre
                    fotoPokemon.load(_detallePokemon.value?.imagen?.url)
                    regionView.text = _detallePokemon.value?.generacion?.nombre
                    tipo1View.text = _detallePokemon.value?.primerTipo?.nombreTipo
                    tipo2View.text = _detallePokemon.value?.segundoTipo?.nombreTipo
                    pCView.text = _detallePokemon.value?.pC.toString()
                    if (_detallePokemon.value!!.estrellas == 1){
                        val1View.load(R.drawable.ic_isfav)
                        val2View.load(R.drawable.ic_nofav)
                        val3View.load(R.drawable.ic_nofav)
                    } else if (_detallePokemon.value!!.estrellas == 2){
                        val1View.load(R.drawable.ic_isfav)
                        val2View.load(R.drawable.ic_isfav)
                        val3View.load(R.drawable.ic_nofav)
                    }else{
                        val1View.load(R.drawable.ic_isfav)
                        val2View.load(R.drawable.ic_isfav)
                        val3View.load(R.drawable.ic_isfav)
                    }

                    ataqueRapidoView.text = _detallePokemon.value?.ataqueRapido
                    ataqueCargadoView.text = _detallePokemon.value?.ataqueCargado

                    if (_detallePokemon.value!!.isOriginal){
                        btnEvolucionar.isGone
                    }

                }
            }

            override fun onFailure(call: Call<DetallePokemon>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}