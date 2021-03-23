package com.salesianostriana.apidexandroid.ui.perfil

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salesianostriana.apidexandroid.data.poko.response.UsuarioRegistroResponse
import com.salesianostriana.apidexandroid.retrofit.UsuarioService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class PerfilViewModel (application: Application) : AndroidViewModel(application) {

    var baseUrl = "http://10.0.2.2:9000/"
    private var _usuario = MutableLiveData<UsuarioRegistroResponse>()
    var service: UsuarioService
    var token: String? = ""

    private val context = getApplication<Application>().applicationContext

    val usuario: LiveData<UsuarioRegistroResponse>
        get() = _usuario


    init {

        val sharedPref = context?.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")

       _usuario.value = UsuarioRegistroResponse("",1, "", "")

        var retrofit = Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .build()



       service = retrofit.create(UsuarioService::class.java)

       getUser()
   }


    fun getUser() {

        service.getUser("Bearer $token")

            .enqueue(object : Callback<UsuarioRegistroResponse> {

                override fun onResponse(call: Call<UsuarioRegistroResponse>, response: Response<UsuarioRegistroResponse>) {

                    if (response.code() == 200){
                        _usuario.value = response.body()!!

                    }
                }

                override fun onFailure(call: Call<UsuarioRegistroResponse>, t: Throwable) {
                    Log.e("Error", t.message.toString())
                }
            })
    }

    fun deleteUser(){
        service.deleteUser("Bearer $token", _usuario.value!!.id.toLong()).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.code()== 204){
                    Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT)
                        .show()
                    /*var intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)*/
                }else{
                    Toast.makeText(context, "La cuenta no se ha podido eliminar", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("Error!!!", t.message.toString())
            }

        })
    }
}