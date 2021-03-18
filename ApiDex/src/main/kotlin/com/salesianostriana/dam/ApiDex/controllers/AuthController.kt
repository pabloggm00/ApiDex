package com.salesianostriana.dam.ApiDex.controllers

import com.salesianostriana.dam.ApiDex.entities.Usuario
import com.salesianostriana.dam.ApiDex.entities.dto.*
import com.salesianostriana.dam.ApiDex.error.UsernameExistsException
import com.salesianostriana.dam.ApiDex.security.jwt.BearerTokenExtractor
import com.salesianostriana.dam.ApiDex.security.jwt.JwtTokenProvider
import com.salesianostriana.dam.ApiDex.services.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val bearerTokenExtractor: BearerTokenExtractor,
    private val usuarioService: UsuarioService
){

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest : LoginRequest) : ResponseEntity<JwtUserResponseLogin> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username, loginRequest.pass
            )
        )

        SecurityContextHolder.getContext().authentication = authentication
        val usuario = authentication.principal as Usuario
        val jwtToken = jwtTokenProvider.generateToken(usuario)
        //val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(usuario)

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponseLogin(jwtToken, usuario.toGetLoginDto()))

    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody newUser: EditUsuarioDto) : ResponseEntity<GetUsuarioRegistradoDto> =
        usuarioService.create(newUser).map { ResponseEntity.status(HttpStatus.CREATED)
            .body(it.toGetUsuarioRegistradoDto())}.orElseThrow {
            UsernameExistsException(newUser.username)
        }

    @PostMapping("/token")
    fun refreshToken(request : HttpServletRequest) : ResponseEntity<JwtUserResponse> {
        val refreshToken = bearerTokenExtractor.getJwtFromRequest(request).orElseThrow {
            ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al procesar el token de refresco")
        }

        try {
            if (jwtTokenProvider.validateRefreshToken(refreshToken)){
                val usuarioId = jwtTokenProvider.getUserIdFromJWT(refreshToken)
                val usuario : Usuario = usuarioService.findById(usuarioId).orElseThrow {
                    UsernameNotFoundException("No se ha podido encontrar el usuario a partir de su ID")
                }

                val jwtToken = jwtTokenProvider.generateToken(usuario)
                val jwtRefreshToken = jwtTokenProvider.generateRefreshToken(usuario)

                return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse(jwtToken,jwtRefreshToken,usuario.toGetLoginDto()))
            }
        }catch (ex : Exception){
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error en la validación del token")
        }

        return ResponseEntity.badRequest().build()
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal usuario : Usuario) = usuario.toGetUsuarioRegistradoDto()
}

data class LoginRequest(
    @NotBlank val username : String,
    @NotBlank val pass: String
)

data class JwtUserResponseLogin(
    val token: String,
    val usuario : GetLoginDto
)

data class JwtUserResponse(
    val token: String,
    val refreshToken: String,
    val usuario : GetLoginDto
)
