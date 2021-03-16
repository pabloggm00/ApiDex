package com.salesianostriana.dam.ApiDex.entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import javax.persistence.*

@Entity
class Usuario(
    var email: String,
    private var username: String,
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