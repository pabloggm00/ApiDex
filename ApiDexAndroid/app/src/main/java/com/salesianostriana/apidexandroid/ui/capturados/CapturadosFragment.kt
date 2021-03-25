package com.salesianostriana.apidexandroid.ui.capturados

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import com.salesianostriana.apidexandroid.ui.favoritos.MyFavoritosRecyclerViewAdapter

class CapturadosFragment : Fragment() {

    private lateinit var capturadosViewModel: CapturadosViewModel
    var pokemonCapturadosList: List<Pokemon> = listOf()
    lateinit var listAdapter: MyCapturadosRecyclerViewAdapter
    lateinit var lista: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        capturadosViewModel =
            ViewModelProvider(this).get(CapturadosViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_capturados_list, container, false)

        lista = view.findViewById(R.id.list_capturados)
        val v = lista

        v.layoutManager = LinearLayoutManager(context)
        listAdapter = MyCapturadosRecyclerViewAdapter(activity as Context, capturadosViewModel, pokemonCapturadosList)
        v.adapter = listAdapter

        capturadosViewModel.pokemon.observe(viewLifecycleOwner, Observer {
            pokemons -> pokemonCapturadosList = pokemons
            listAdapter.setData(pokemons)
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        capturadosViewModel.getPokemonCapturados()
    }
}