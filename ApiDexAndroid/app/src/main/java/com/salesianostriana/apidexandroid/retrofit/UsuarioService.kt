package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsuarioService {

    @GET("user/me")
    fun getUser(@Header("Authorization")token: String?): Call<UsuarioRegistroResponse>

    @DELETE("user/{id}")
    fun deleteUser(@Header("Authorization")token: String?, @Path("id") id: Long): Call<Any>
}