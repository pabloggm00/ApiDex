package com.salesianostriana.apidexandroid.ui.perfil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import coil.load
import com.salesianostriana.apidexandroid.MainActivity
import com.salesianostriana.apidexandroid.R
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


class EditarPerfilActivity : AppCompatActivity() {


    private val baseUrl= "http://10.0.2.2:9000/"
    lateinit var service: UsuarioService
    lateinit var retrofit: Retrofit
    val context = this
    var token: String? = ""
    private var _usuario = MutableLiveData<UsuarioRegistroResponse>()

    val PICK_IMAGE = 100
    val REQUEST_CODE = 100
    lateinit var body: MultipartBody.Part

    val uriPathHelper = URIPathHelper()
    lateinit var selectedImage: Uri
     var filePath: String? = ""


    lateinit var btnGuardar: Button
    lateinit var editEmail: EditText
    lateinit var textViewUsername: TextView
    lateinit var avatar: ImageView
    /*var fotoPerfil: String? = ""*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)


        val sharedPref = this.getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref?.getString("token", "")

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(UsuarioService::class.java)

        /*fotoPerfil = intent.extras?.getString("avatar")*/

        textViewUsername = findViewById(R.id.textView_Username_edit)
        editEmail = findViewById(R.id.editTextEmail)
        avatar = findViewById(R.id.imageViewEditAvatar)
        btnGuardar = findViewById(R.id.btn_guardar)

        getUser()


        avatar.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
        })

        btnGuardar.setOnClickListener(View.OnClickListener {
            postImage()
            var intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        })

        val reqFile: RequestBody = RequestBody.create(MediaType.parse("image/*"), filePath!!)
        body = MultipartBody.Part.createFormData("upload", filePath, reqFile)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            avatar.setImageURI(data?.data) // handle chosen image
            selectedImage = data?.getData()!!
            filePath = context.let {uriPathHelper.getPath(context, selectedImage) }


            println(selectedImage)
            println(filePath)


        }
    }

    fun getUser() {

        service.getUser("Bearer $token")

                .enqueue(object : Callback<UsuarioRegistroResponse> {

                    override fun onResponse(call: Call<UsuarioRegistroResponse>, response: Response<UsuarioRegistroResponse>) {

                        if (response.code() == 200) {
                            _usuario.value = response.body()!!

                            textViewUsername.text = _usuario.value!!.username
                            editEmail.hint = _usuario.value!!.email
                            /*avatar.load(_usuario.value!!.avatar)*/


                        }
                    }

                    override fun onFailure(call: Call<UsuarioRegistroResponse>, t: Throwable) {
                        Log.e("Error", t.message.toString())
                    }
                })
    }

    fun postImage(){
        service.postImage(body, "Bearer $token").enqueue(object : Callback<UsuarioRegistroResponse> {
            override fun onResponse(
                    call: Call<UsuarioRegistroResponse>,
                    response: Response<UsuarioRegistroResponse>
            ) {

                println(response.code())


            }

            override fun onFailure(call: Call<UsuarioRegistroResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}