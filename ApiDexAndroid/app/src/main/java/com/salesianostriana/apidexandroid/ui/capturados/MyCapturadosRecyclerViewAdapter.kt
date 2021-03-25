package com.salesianostriana.apidexandroid.ui.capturados

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.response.Pokemon
import com.salesianostriana.apidexandroid.ui.detallePokemon.DetallePokemonActivity


class MyCapturadosRecyclerViewAdapter(
    private val activity: Context,
    private val viewModel: CapturadosViewModel,
    private var values: List<Pokemon>
) : RecyclerView.Adapter<MyCapturadosRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_capturados, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreView: TextView = view.findViewById(R.id.textView_nombrePokemon)
        val idPokedex: TextView = view.findViewById(R.id.textView_idPokedex)
        val fav: ImageView = view.findViewById(R.id.imageView_favoritoDetalle)
        val fotoPokemon: ImageView = view.findViewById(R.id.imageView_fotoPokemon)
        val rootView: View = view.findViewById(R.id.capturados_view)
        val capturado: ImageView = view.findViewById(R.id.imageView_noCapturadoDetalle)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nombreView.text = item.nombre
        holder.idPokedex.text = item.idPokedex
        holder.fotoPokemon.load(item.imagen)
        var isCapturado= item.isCapturado
        var megusta= item.isFav


        if(isCapturado){
            holder.capturado.load(R.drawable.ic_capturado)
        } else{
            holder.capturado.load(R.drawable.ic_nocapturado)
        }

        if(megusta){
            holder.fav.load(R.drawable.ic_isfav)
        } else{
            holder.fav.load(R.drawable.ic_nofav)
        }

        holder.rootView.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, DetallePokemonActivity::class.java).apply {
                putExtra("pokemonId", item.id)
            }
            activity.startActivity(intent)
        })
        holder.fav.setOnClickListener(View.OnClickListener {
            viewModel.addPokemonFav(item.id, item.isFav)
            viewModel.getPokemonCapturados()
        })

        holder.capturado.setOnClickListener(View.OnClickListener {
            viewModel.addPokemonCapturado(item.id, item.isCapturado)
            viewModel.getPokemonCapturados()
        })

    }

    fun setData(newPokemon: List<Pokemon>){
        this.values = newPokemon
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size


}