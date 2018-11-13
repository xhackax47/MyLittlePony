package fr.xhackax47.MyLittlePony.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xhackax47.MyLittlePony.dao.RoleRepository;
import fr.xhackax47.MyLittlePony.dao.UserRepository;
import fr.xhackax47.MyLittlePony.exception.ResourceNotFoundException;
import fr.xhackax47.MyLittlePony.models.JwtAuthenticationResponse;
import fr.xhackax47.MyLittlePony.models.LoginRequest;
import fr.xhackax47.MyLittlePony.models.User;
import fr.xhackax47.MyLittlePony.security.JwtTokenProvider;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;    

    @Autowired
    JwtTokenProvider tokenProvider;
	
    @CrossOrigin(origins = "*")
	@GetMapping("/") 
    @Secured("ROLE_ADMIN")
	public Collection<User> getAllUsers() {
		return (Collection<User>) userRepository.findAll();
	}
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value="id") Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));		
		return user;
	}
    
    @CrossOrigin(origins = "*")
	@GetMapping("/user/{login}")
	public User getUserByName(@PathVariable String username) throws Exception {
    	List<User> listUsr = userRepository.findAll();
    	List<User> filteredListUsr = listUsr.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
    	if(filteredListUsr.isEmpty()) {
    		throw new Exception();
    	}else {
    		return filteredListUsr.get(0);
    	}
	}
	
    @CrossOrigin(origins = "*")
	@PostMapping("/register")
	public User addUser(@RequestBody User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
    
    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    // Connexion de compte utilisateur
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
	
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	public User updateUser(@PathVariable(value="id") Long id, @Valid @RequestBody User u) {
    	User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
		user.setUsername(u.getUsername());
		user.setPassword(u.getPassword());
		User userUp = userRepository.save(user);
		return userUp;
	}
	
    @Secured({"ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
    	userRepository.deleteById(id);
	}
    
}
