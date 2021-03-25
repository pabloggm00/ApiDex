package com.salesianostriana.apidexandroid.ui.favoritos

import android.content.Context
import android.os.Bundle
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

class FavoritosFragment : Fragment() {

    private lateinit var favoritosViewModel: FavoritosViewModel
    var pokemonFavsList: List<Pokemon> = listOf()
    lateinit var listAdapter: MyFavoritosRecyclerViewAdapter
    lateinit var lista: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritosViewModel =
            ViewModelProvider(this).get(FavoritosViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_favoritos_list, container, false)

        lista = view.findViewById(R.id.list_favoritos)
        val v = lista

        v.layoutManager = LinearLayoutManager(context)

        listAdapter = MyFavoritosRecyclerViewAdapter(activity as Context, favoritosViewModel, pokemonFavsList)
        v.adapter = listAdapter

        favoritosViewModel.pokemon.observe(viewLifecycleOwner, Observer {
            pokemons -> pokemonFavsList = pokemons
            listAdapter.setData(pokemons)
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        favoritosViewModel.getPokemonFavs()
    }
}