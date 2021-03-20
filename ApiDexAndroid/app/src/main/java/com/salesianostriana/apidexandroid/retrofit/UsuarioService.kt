package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface UsuarioService {

    @GET("user/me")
    fun getUser(@Header("Authorization")token: String?): Call<UsuarioRegistroResponse>
}