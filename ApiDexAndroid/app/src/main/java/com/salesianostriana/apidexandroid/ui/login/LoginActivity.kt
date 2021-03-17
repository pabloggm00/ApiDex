package com.salesianostriana.apidexandroid.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.salesianostriana.apidexandroid.MainActivity

import com.salesianostriana.apidexandroid.R
import com.salesianostriana.apidexandroid.data.poko.request.LoginRequest
import com.salesianostriana.apidexandroid.data.poko.response.LoginResponse
import com.salesianostriana.apidexandroid.retrofit.AuthService
import com.salesianostriana.apidexandroid.ui.registro.RegistroActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    var baseUrl = "http://10.0.2.2:9000/"


    lateinit var retrofit: Retrofit
    lateinit var service: AuthService
    val context = this


    lateinit var btnLogin: Button
    lateinit var editTextUsername: EditText
    lateinit var editTextPass: EditText
    lateinit var textGoSignUp: TextView
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        btnLogin = findViewById(R.id.login)
        editTextUsername = findViewById(R.id.username)
        editTextPass = findViewById(R.id.password)
        textGoSignUp = findViewById(R.id.text_view_go_signup)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(AuthService::class.java)

        btnLogin.setOnClickListener(View.OnClickListener {
            doLogin()
        })

        textGoSignUp.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, RegistroActivity::class.java)
            this.startActivity(intent)
        })


    }

    fun doLogin() {

        val loginData = LoginRequest(editTextUsername.text.toString(), editTextPass.text.toString())

        service.login(loginData).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.code() == 201) {

                    var intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    //TODO Guardar el token
                    val sharedPref =
                        context.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)

                    with(sharedPref.edit()) {

                        putString("token", response.body()?.token)
                        commit()
                    }
                } else {
                    Toast.makeText(context, "Login incorrecto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("error", t.message.toString())

            }
        })

    }
}

