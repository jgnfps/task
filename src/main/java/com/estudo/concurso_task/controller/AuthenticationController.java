package com.estudo.concurso_task.controller;

import com.estudo.concurso_task.Infra.Security.TokenService;
import com.estudo.concurso_task.dto.AuthenticationDTO;
import com.estudo.concurso_task.dto.LoginResponseDTO;
import com.estudo.concurso_task.dto.RegisterDTO;
import com.estudo.concurso_task.entity.User;
import com.estudo.concurso_task.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT para acesso às rotas protegidas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
            }
    )
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário com nome, senha e papel (ROLE)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Usuário já existente")
            }
    )
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        if (this.userRepository.findByUsername(dto.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        User newUser = new User(dto.username(), encryptedPassword, dto.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
