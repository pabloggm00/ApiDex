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

    val PICK_IMAGE = 100
    val REQUEST_CODE = 100

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
        eliminarCuenta = v.findViewById(R.id.btn_eliminarCuenta)

        avatar.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
            
        })

        eliminarCuenta.setOnClickListener(View.OnClickListener {
            viewModel.deleteUser()
            var intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)

        })

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            avatar.setImageURI(data?.data) // handle chosen image
        }
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