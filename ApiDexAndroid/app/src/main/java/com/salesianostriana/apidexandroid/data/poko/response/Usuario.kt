package com.salesianostriana.apidexandroid.data.poko.response

data class Usuario (
     var email: String,
     var username: String,
     var pass: String,
     val id: Long? = null
)