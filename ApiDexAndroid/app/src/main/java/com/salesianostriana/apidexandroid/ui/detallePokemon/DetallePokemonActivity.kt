package com.salesianostriana.apidexandroid.ui.detallePokemon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import coil.load
import com.salesianostriana.apidexandroid.MainActivity
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.response.DetallePokemon
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import com.salesianostriana.apidexandroid.retrofit.PokemonService
import com.salesianostriana.apidexandroid.ui.nuevoPokemon.NuevoPokemonActivity
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
    val context = this
    var pokemonFav: Boolean = false
    var pokemonCap: Boolean = false
    var pokemonPC: Int = 0

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
    lateinit var cardViewSegundotipo: CardView
    lateinit var cardViewPrimertipo: CardView

    lateinit var tituloAtaqueRapido : TextView
    lateinit var tituloAtaqueCargado : TextView
    lateinit var tituloValoracion: TextView
    lateinit var tituloPC: TextView


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        /*if (!_detallePokemon.value!!.isOriginal)*/
            inflater.inflate(R.menu.detalle_pokemon_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, NuevoPokemonActivity::class.java).apply{
                    putExtra("editar", true)
                    putExtra("idPokemon", pokemonId)
                }
                this.startActivity(intent)
                true
            }

            R.id.action_delete -> {
                deletePokemon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pokemon)

        val sharedPref = this.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")

        pokemonFav = intent.getBooleanExtra("pokemonFav", false)
        pokemonCap = intent.getBooleanExtra("pokemonCap", false)
        pokemonId = intent.extras?.getLong("pokemonId")

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(PokemonService::class.java)


        tituloAtaqueRapido  = findViewById(R.id.textView_tituloAtaqueR)
        tituloAtaqueCargado  = findViewById(R.id.textView_tituloAtaqueC)
        tituloValoracion = findViewById(R.id.textView_valoracion)
        tituloPC = findViewById(R.id.textView_PC)

        idPokedexView = findViewById(R.id.textView_idPokedex)
        isFavView = findViewById(R.id.imageView_favoritoDetalle)
        isCapView = findViewById(R.id.imageView_noCapturadoDetalle)
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
        cardViewSegundotipo = findViewById(R.id.cardView_tipo2)
        cardViewPrimertipo = findViewById(R.id.cardView_tipo1)


        btnDuplicar.setOnClickListener ( View.OnClickListener {
            var intent = Intent(context, NuevoPokemonActivity::class.java).apply {
                putExtra("idPokemon", pokemonId)
            }
            context.startActivity(intent)

        } )

        btnEvolucionar.setOnClickListener(View.OnClickListener {
            evolucionarPokemon()
        })

        isFavView.setOnClickListener(View.OnClickListener {
            addPokemonFav(pokemonId!!.toLong(),pokemonFav)
            getDetallePokemon()
        })

        isCapView.setOnClickListener(View.OnClickListener {
            addPokemonCapturado(pokemonId!!.toLong(), pokemonCap)
            getDetallePokemon()
        })

        getDetallePokemon()



    }

    fun cambiarTitulo(titulo: String?){
        title = titulo
    }

    fun getDetallePokemon(){
        service.getDetallePokemon("Bearer ${token}", pokemonId!!).enqueue(object : Callback<DetallePokemon>{

            override fun onResponse(call: Call<DetallePokemon>, response: Response<DetallePokemon>) {
                if (response.code() == 200){
                    _detallePokemon.value = response.body()

                    cambiarTitulo(_detallePokemon.value?.nombre)

                    idPokedexView.text = _detallePokemon.value?.idPokedex

                    Log.i("is fav",pokemonFav.toString())
                    Log.i("is fav",pokemonCap.toString())

                    if (pokemonFav) {
                        isFavView.load(R.drawable.ic_isfav)
                    }else {
                        isFavView.load(R.drawable.ic_nofav)
                    }

                    if (pokemonCap) {
                        isCapView.load(R.drawable.ic_capturado)
                    }else {
                        isCapView.load(R.drawable.ic_nocapturado)
                    }
                    nombreView.text = _detallePokemon.value?.nombre
                    fotoPokemon.load(_detallePokemon.value?.imagen?.url)
                    regionView.text = _detallePokemon.value?.generacion

                    /*when (_detallePokemon.value!!.primerTipo) {
                        "Planta" ->{ tipo1View.setTextColor(R.color.red)
                            //cardViewPrimertipo.setCardBackgroundColor(R.color.planta)
                        }

                    }*/


                    tipo1View.text = _detallePokemon.value?.primerTipo

                    if (_detallePokemon.value!!.segundoTipo == null)
                        cardViewSegundotipo.visibility = View.GONE
                    else
                        tipo2View.text = _detallePokemon.value?.segundoTipo

                    pCView.text = _detallePokemon.value?.pC.toString()
                    if (_detallePokemon.value!!.estrellas == 1){
                        val1View.load(R.drawable.ic_val)
                        val2View.load(R.drawable.ic_nofav)
                        val3View.load(R.drawable.ic_nofav)
                    } else if (_detallePokemon.value!!.estrellas == 2){
                        val1View.load(R.drawable.ic_val)
                        val2View.load(R.drawable.ic_val)
                        val3View.load(R.drawable.ic_nofav)
                    }else{
                        val1View.load(R.drawable.ic_val)
                        val2View.load(R.drawable.ic_val)
                        val3View.load(R.drawable.ic_val)
                    }

                    ataqueRapidoView.text = _detallePokemon.value?.ataqueRapido
                    ataqueCargadoView.text = _detallePokemon.value?.ataqueCargado

                    if (_detallePokemon.value!!.isOriginal) {
                        val1View.visibility = View.GONE
                        val2View.visibility = View.GONE
                        val3View.visibility = View.GONE
                        tituloPC.visibility = View.GONE
                        tituloValoracion.visibility = View.GONE
                        tituloAtaqueRapido.visibility = View.GONE
                        tituloAtaqueCargado.visibility = View.GONE
                        pCView.visibility = View.GONE
                        btnEvolucionar.visibility = View.GONE
                    }

                    if (_detallePokemon.value!!.isUltimo) {
                        btnEvolucionar.visibility = View.GONE
                    }

                }
            }

            override fun onFailure(call: Call<DetallePokemon>, t: Throwable) {
                Log.e("Error!!!", t.message.toString())
            }

        })
    }

    fun evolucionarPokemon(){
        service.evolucionarPokemon("Bearer $token", pokemonId!!.toLong()).enqueue(object : Callback<DetallePokemon>{
            override fun onResponse(
                call: Call<DetallePokemon>,
                response: Response<DetallePokemon>
            ) {
                if (response.code() == 201){
                    Toast.makeText(context, "Evolucionado!!!", Toast.LENGTH_SHORT)
                        .show()
                    /*var intent = Intent(context, DetallePokemonActivity::class.java).apply {
                    putExtra("pokemonId", pokemonData.)
                }*/
                    var intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                } else {
                    Log.i("code", response.code().toString())
                    Toast.makeText(context, "No se ha podido evolucionar", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<DetallePokemon>, t: Throwable) {
                Log.e("Error!!!", t.message.toString())
            }

        })
    }

    fun deletePokemon(){
        service.deletePokemon("Bearer $token", pokemonId!!.toLong()).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                if(response.code() == 204){

                    if (_detallePokemon.value!!.isOriginal){
                        Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT)
                            .show()
                        var intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }else{
                        Toast.makeText(context, "Borrado correctamente", Toast.LENGTH_SHORT)
                            .show()
                        var intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                }

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("Error!!!", t.message.toString())
            }

        })
    }

    fun addPokemonCapturado(pokemonId: Long, isCapturado: Boolean) {

        if (!isCapturado) {
            service.addPokemonCapturado("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 204) {
                        getDetallePokemon()
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
                        getDetallePokemon()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }

    fun addPokemonFav(pokemonId: Long, isFav: Boolean) {

        if (!isFav) {
            service.addPokemonFav("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 204){
                        getDetallePokemon()
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
                        getDetallePokemon()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }


}