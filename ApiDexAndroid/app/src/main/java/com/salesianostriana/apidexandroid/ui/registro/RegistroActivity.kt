package com.salesianostriana.apidexandroid.ui.registro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.request.UsuarioRequest
import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import com.salesianostriana.apidexandroid.retrofit.AuthService
import com.salesianostriana.apidexandroid.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class RegistroActivity : AppCompatActivity() {


    var baseUrl = "http://10.0.2.2:9000/auth/"

    lateinit var retrofit: Retrofit
    lateinit var service: AuthService
    val context = this

    lateinit var btnRegistro: Button
    lateinit var editTextUsername: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextEmail: EditText
    lateinit var textGoLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        supportActionBar?.hide()

        btnRegistro = findViewById(R.id.button_registro)
        editTextUsername = findViewById(R.id.edit_text_username)
        editTextPassword = findViewById(R.id.edit_text_password)
        editTextEmail = findViewById(R.id.edit_text_email)
        textGoLogin = findViewById(R.id.text_view_go_login)


        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(AuthService::class.java)

        btnRegistro.setOnClickListener(View.OnClickListener {
            doSignUp()
        })

        textGoLogin.setOnClickListener(View.OnClickListener {
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        })
    }

    fun doSignUp() {
        val registerData = UsuarioRequest(editTextEmail.text.toString(), editTextPassword.text.toString(), editTextUsername.text.toString())

        service.registroUsuario(registerData).enqueue(object : Callback<UsuarioRegistroResponse>{

            override fun onResponse(call: Call<UsuarioRegistroResponse>, response: Response<UsuarioRegistroResponse>) {
                if(response.code() == 201) {
                    Toast.makeText(context, "Registro realizado correctamente", Toast.LENGTH_SHORT).show()
                    var intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)

                }
                else{

                    if (editTextUsername.length() < 3){
                        Toast.makeText(context, "El usuario debe tener al menos 4 carácteres", Toast.LENGTH_SHORT).show()
                    }
                    if (editTextPassword.length() < 9){
                        Toast.makeText(context, "La contraseña debe ser mayor de 9", Toast.LENGTH_SHORT).show()
                    }
                    if (editTextEmail.contentDescription == "@"){
                        Toast.makeText(context, "El email debe ser de tipo email", Toast.LENGTH_SHORT).show()
                    }

                    Toast.makeText(context, "No se ha podido registrar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioRegistroResponse>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })
    }
}