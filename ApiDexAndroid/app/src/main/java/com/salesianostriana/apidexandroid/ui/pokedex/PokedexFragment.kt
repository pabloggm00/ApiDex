package com.salesianostriana.apidexandroid.ui.pokedex

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

class PokedexFragment : Fragment() {

    private lateinit var pokedexViewModel: PokedexViewModel
    var pokemonList: List<Pokemon> = listOf()
    lateinit var listAdapter: MyPokedexRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokedexViewModel =
            ViewModelProvider(this).get(PokedexViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_pokedex_list, container, false)

        val v = view as RecyclerView

        v.layoutManager = LinearLayoutManager(context)
        listAdapter = MyPokedexRecyclerViewAdapter(activity as Context, pokedexViewModel, pokemonList)
        v.adapter = listAdapter

        pokedexViewModel.pokemon.observe(viewLifecycleOwner, Observer {
            pokemons -> pokemonList = pokemons
            Log.i("Pokemooonsssss", pokemonList.toString())
            listAdapter.setData(pokemons)
        })

        return view
    }
}