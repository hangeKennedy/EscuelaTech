package com.cibertec.auth;

import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.auth.exceptions.UsuarioExistenteException;
import com.cibertec.auth.exceptions.UsuarioNoEncontradoException;
import com.cibertec.model.User;
import com.cibertec.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	private final UserRepository userRepository;
	private final AuthService authService;

	@PostMapping(value = "login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			AuthResponse authResponse = authService.login(request);
			return ResponseEntity.ok(authResponse);
		} catch (UsuarioNoEncontradoException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al iniciar sesión.");
		}
	}

	@PostMapping(value = "registerStudent")
	public ResponseEntity<?> registerStudent(@RequestBody RegisterRequest request) {
		try {
			AuthResponse authResponse = authService.registerStudent(request);
			return ResponseEntity.ok(authResponse);
		} catch (UsuarioExistenteException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario");
		}
	}

	@PostMapping(value = "registerAdmin")
	public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
		try {
			AuthResponse authResponse = authService.registerAdmin(request);
			return ResponseEntity.ok(authResponse);
		} catch (UsuarioExistenteException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario");
		}
	}

	@GetMapping(value = "actual-usuario")
	public ResponseEntity<Optional<User>> obtenerUsuarioActual(Principal principal) {
		Optional<User> user = this.userRepository.findByUsername(principal.getName());
		return ResponseEntity.ok(user);
	}
}
