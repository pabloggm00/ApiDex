package com.salesianostriana.apidexandroid.ui.detallePokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.salesianostriana.apidexandroid.R

class DetallePokemonActivity : AppCompatActivity() {


    private var _detallePokemon = MutableLiveData<De>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pokemon)
    }
}