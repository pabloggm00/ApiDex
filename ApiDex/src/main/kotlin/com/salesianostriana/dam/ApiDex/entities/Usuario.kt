package com.salesianostriana.dam.ApiDex.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
class Usuario(

    @get:Email(message = "{usuario.email.blank}")
    var email: String,

    @get:NotBlank(message = "{usuario.username.blank}")
    @get:Size( message = "{usuario.username.size}", min= 4, max= 20)
    @Column(unique = true)
    private var username: String,

    @get:Size( message = "{usuario.password.size}", min= 8, max = 100)
    var pass: String,


    @ElementCollection(fetch = FetchType.EAGER)
    val roles: MutableSet<String> = HashSet(),

    private val nonExpired: Boolean = true,

    private val nonLocked: Boolean = true,

    var activo: Boolean = true,

    private val credentialIsNonExpired: Boolean = true,



    @ManyToMany
    @JoinTable(name = "favoritos",
        joinColumns = [JoinColumn(name="usuario_id")],
        inverseJoinColumns = [JoinColumn(name="pokemon_id")]
    )
    var pokemonsFavs: MutableList<Pokemon> = mutableListOf(),

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "imagen_id", referencedColumnName = "id")
    var avatar: Imagen? = null,

    @OneToMany(mappedBy = "usuario")
    var listaEquipos: MutableList<Equipo> = mutableListOf(),

    @ManyToMany
    @JoinTable(name = "capturados",
        joinColumns = [JoinColumn(name="usuario_id")],
        inverseJoinColumns = [JoinColumn(name="pokemon_id")]
    )
    var pokemonsCapturados: MutableList<Pokemon> = mutableListOf(),


    @Id @GeneratedValue
    val id: Long? = null
) : UserDetails {

    constructor(email: String, username: String, pass: String, role: String) :
            this(email, username, pass, mutableSetOf(role),true, true, true, true)


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority("ROLE_$it") }.toMutableList()
    }

    override fun isEnabled()= activo
    override fun getUsername() = username
    override fun getPassword() = pass
    override fun isCredentialsNonExpired() = credentialIsNonExpired
    override fun isAccountNonExpired() = nonExpired
    override fun isAccountNonLocked() = nonLocked


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as Usuario
        if (id != that.id) return false
        return true
    }


    override fun hashCode(): Int {
        return if (id != null)
            id.hashCode()
        else 0
    }

}