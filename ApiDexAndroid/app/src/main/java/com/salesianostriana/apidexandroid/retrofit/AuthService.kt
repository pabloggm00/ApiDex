package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.request.LoginRequest
import com.salesianostriana.apidexandroid.data.poko.request.UsuarioRequest
import com.salesianostriana.apidexandroid.data.poko.response.LoginResponse
import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("register")
    fun registroUsuario(@Body newUser: UsuarioRequest): Call<UsuarioRegistroResponse>

    @POST("auth/login")
    fun login(@Body requestLogin: LoginRequest): Call<LoginResponse>
}