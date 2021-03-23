package com.salesianostriana.apidexandroid.ui.perfil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.ui.login.LoginActivity


class PerfilFragment : Fragment() {

    companion object {
        fun newInstance() = PerfilFragment()
    }

    private lateinit var viewModel: PerfilViewModel

    lateinit var textViewUsername: TextView
    lateinit var textViewEmail: TextView
    lateinit var avatar: ImageView
    lateinit var eliminarCuenta: Button
    lateinit var btnEditar: Button

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
        eliminarCuenta = v.findViewById(R.id.btn_eliminarCuenta)
        btnEditar = v.findViewById(R.id.btn_editarPerfil)

        btnEditar.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, EditarPerfilActivity::class.java).apply {
                putExtra("avatar", viewModel.usuario.value!!.avatar)
            }
            context?.startActivity(intent)
        })

        eliminarCuenta.setOnClickListener(View.OnClickListener {
            viewModel.deleteUser()
            var intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)

        })

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
        })
    }

}