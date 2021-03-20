package com.salesianostriana.apidexandroid.ui.perfil

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import coil.load
import com.salesianostriana.apidexandroid.R

class PerfilFragment : Fragment() {

    companion object {
        fun newInstance() = PerfilFragment()
    }
    private lateinit var viewModel: PerfilViewModel

    lateinit var textViewUsername: TextView
    lateinit var textViewEmail: TextView
    lateinit var avatar: ImageView
    lateinit var eliminarCuenta: Button

    var token: String?  = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v =  inflater.inflate(R.layout.perfil_fragment, container, false)


        val sharedPref =
            context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")


        textViewUsername = v.findViewById(R.id.profile_username)
        textViewEmail = v.findViewById(R.id.textView_email)
        avatar = v.findViewById(R.id.profile_avatar)



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PerfilViewModel::class.java)

        viewModel.token = token
        viewModel.usuario.observe(viewLifecycleOwner, Observer {
            user ->
            textViewUsername.text = user.username
            textViewEmail.text = user.email
            avatar.load(user.avatar)
            Log.e("USERNAME:", "${user.username}")
        })
    }

}