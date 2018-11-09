package fr.xhackax47.MyLittlePony.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xhackax47.MyLittlePony.dao.UserRepository;
import fr.xhackax47.MyLittlePony.exception.ResourceNotFoundException;
import fr.xhackax47.MyLittlePony.models.User;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
    @CrossOrigin(origins = "*")
	@GetMapping("/") 
    @Secured("ROLE_ADMIN")
	public Collection<User> getAllUsers() {
		return (Collection<User>) userRepo.findAll();
	}
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value="id") Long id) {
    	User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));		
		return user;
	}
    
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@GetMapping("/user/{login}")
	public User getUserByName(@PathVariable String username) throws Exception {
    	List<User> listUsr = userRepo.findAll();
    	List<User> filteredListUsr = listUsr.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
    	if(filteredListUsr.isEmpty()) {
    		throw new Exception();
    	}else {
    		return filteredListUsr.get(0);
    	}
	}
	
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@PostMapping("/")
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	public User updateUser(@PathVariable(value="id") Long id, @Valid @RequestBody User u) {
    	User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
		user.setUsername(u.getUsername());
		user.setPassword(u.getPassword());
		User userUp = userRepo.save(user);
		return userUp;
	}
	
    @Secured({"ROLE_ADMIN"})
    @CrossOrigin(origins = "*")
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userRepo.deleteById(id);
	}

}
