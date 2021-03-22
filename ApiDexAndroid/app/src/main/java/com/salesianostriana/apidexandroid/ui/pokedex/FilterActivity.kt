package com.salesianostriana.apidexandroid.ui.pokedex

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.view.get
import com.salesianostriana.apidexandroid.MainActivity
import com.salesianostriana.apidexandroid.R

class FilterActivity : AppCompatActivity() {

    lateinit var spinnerTipos: Spinner
    lateinit var spinnerGeneracion: Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)


        spinnerTipos  = findViewById(R.id.tipos_spinner)
        spinnerGeneracion = findViewById(R.id.generacion_spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.tipos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipos.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.generacion_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGeneracion.adapter = adapter
        }

        var btn: Button = findViewById(R.id.btn_buscar)

        btn.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)

            var generacion: String = spinnerGeneracion.selectedItem.toString().toLowerCase()
            var tipo: String = spinnerTipos.selectedItem.toString().toLowerCase()

            val sharedPref = this.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("generacion", generacion)
                putString("tipo", tipo)
                commit()
            }
        })


    }
}