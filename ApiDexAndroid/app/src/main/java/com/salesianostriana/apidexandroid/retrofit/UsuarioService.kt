package com.salesianostriana.apidexandroid.retrofit

import com.salesianostriana.apidexandroid.data.poko.request.UsuarioEditRequest
import com.salesianostriana.apidexandroid.data.poko.response.Imagen
import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {

    @GET("user/me")
    fun getUser(@Header("Authorization")token: String?): Call<UsuarioRegistroResponse>

    @DELETE("user/{id}")
    fun deleteUser(@Header("Authorization")token: String?, @Path("id") id: Long): Call<Any>

    @Multipart
    @POST("user/img")
    fun postImage(@Part file: MultipartBody.Part, @Header("Authorization")token: String? ) : Call<UsuarioRegistroResponse>

    @PUT("user/{id}")
    fun editUser(@Header("Authorization")token: String?, @Path("id")id: Long, @Body editUser: UsuarioEditRequest) : Call<UsuarioRegistroResponse>
}