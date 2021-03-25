package com.salesianostriana.apidexandroid.ui.detallePokemon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
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
    lateinit var cardView: CardView
    lateinit var layoutView: LinearLayout

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
        cardView = findViewById(R.id.cardView_detallePokemon)
        layoutView = findViewById(R.id.layout_detallePokemon)

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
            addPokemonFav(pokemonId!!.toLong())
        })

        isCapView.setOnClickListener(View.OnClickListener {
            addPokemonCapturado(pokemonId!!.toLong())
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

                    when (_detallePokemon.value!!.primerTipo) {
                        "Fuego" ->{
                            //cardViewPrimertipo.setCardBackgroundColor(Color.parseColor("#7d2d00"))
                            cardView.setCardBackgroundColor(Color.parseColor("#AA5B1E"))
                            layoutView.setBackgroundColor(Color.parseColor("#AA5B1E"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#7d2d00"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#7d2d00"))
                        }
                        "Agua" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#FF0C147C"))
                            layoutView.setBackgroundColor(Color.parseColor("#FF0C147C"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#000062"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#000062"))
                        }
                        "Veneno" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#FF4C3287"))
                            layoutView.setBackgroundColor(Color.parseColor("#FF4C3287"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#1b0a59"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#1b0a59"))
                        }
                        "Acero" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#235253"))
                            layoutView.setBackgroundColor(Color.parseColor("#235253"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#00292b"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#00292b"))
                        }
                        "Bicho" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#376127"))
                            layoutView.setBackgroundColor(Color.parseColor("#376127"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#0b3600"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#0b3600"))
                        }
                        "Dragón" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#352455"))
                            layoutView.setBackgroundColor(Color.parseColor("#352455"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#11002c"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#11002c"))
                        }
                        "Eléctrico" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#403D01"))
                            layoutView.setBackgroundColor(Color.parseColor("#403D01"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#201600"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#201600"))
                        }
                        "Hada" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#8C448B"))
                            layoutView.setBackgroundColor(Color.parseColor("#8C448B"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#5d165d"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#5d165d"))
                        }
                        "Lucha" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#771313"))
                            layoutView.setBackgroundColor(Color.parseColor("#771313"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#470000"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#470000"))
                        }
                        "Normal" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#534B37"))
                            layoutView.setBackgroundColor(Color.parseColor("#534B37"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#2a2311"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#2a2311"))
                        }
                        "Siniestro" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#443753"))
                            layoutView.setBackgroundColor(Color.parseColor("#443753"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#1c112a"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#1c112a"))
                        }
                        "Tierra" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#886B23"))
                            layoutView.setBackgroundColor(Color.parseColor("#886B23"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#584100"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#584100"))
                        }
                        "Psíquico" -> {
                            cardView.setCardBackgroundColor(Color.parseColor("#921397"))
                            layoutView.setBackgroundColor(Color.parseColor("#921397"))
                            btnDuplicar.setBackgroundColor(Color.parseColor("#600068"))
                            btnEvolucionar.setBackgroundColor(Color.parseColor("#600068"))
                        }

                    }






                    tipo1View.text = _detallePokemon.value?.primerTipo

                    if (_detallePokemon.value!!.segundoTipo == null)
                        cardViewSegundotipo.visibility = View.GONE
                    else
                        tipo2View.text = _detallePokemon.value?.segundoTipo

                    pCView.text = _detallePokemon.value?.pc.toString()
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
                        ataqueRapidoView.visibility = View.GONE
                        ataqueCargadoView.visibility = View.GONE
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

    fun addPokemonCapturado(pokemonId: Long) {

        if (!pokemonCap) {
            service.addPokemonCapturado("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 201) {
                        pokemonCap = !pokemonCap
                        if (pokemonCap) {
                            isCapView.load(R.drawable.ic_capturado)
                        }else {
                            isCapView.load(R.drawable.ic_nocapturado)
                        }
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
                        pokemonCap = !pokemonCap
                        if (pokemonCap) {
                            isCapView.load(R.drawable.ic_capturado)
                        }else {
                            isCapView.load(R.drawable.ic_nocapturado)
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }

    fun addPokemonFav(pokemonId: Long) {

        if (!pokemonFav) {
            service.addPokemonFav("Bearer ${token}", pokemonId).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.code() == 201){
                        pokemonFav = !pokemonFav
                        if (pokemonFav) {
                            isFavView.load(R.drawable.ic_isfav)
                        }else {
                            isFavView.load(R.drawable.ic_nofav)
                        }
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
                        pokemonFav = !pokemonFav
                        if (pokemonFav) {
                            isFavView.load(R.drawable.ic_isfav)
                        }else {
                            isFavView.load(R.drawable.ic_nofav)
                        }
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }

            })
        }
    }


}