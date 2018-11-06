package fr.xhackax47.MyLittlePony.controller;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xhackax47.MyLittlePony.dao.PonyRepository;
import fr.xhackax47.MyLittlePony.exception.ResourceNotFoundException;
import fr.xhackax47.MyLittlePony.models.Pony;

@RestController
@RequestMapping("/api/ponies")
public class PonyController {
	
	@Autowired
	private PonyRepository repo;
	
	@GetMapping("/") 
	public Collection<Pony> getAllPonies() {
		return (Collection<Pony>) repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Pony> getPonyById(@PathVariable Long id) {
		return repo.findById(id);
	}
	
	@PostMapping("/")
	public Pony addPony(@RequestBody Pony p) {
		return repo.save(p);
	}
	
	@PutMapping("/{id}")
	public Pony updatePony(@PathVariable(value="id") Long id, @Valid @RequestBody Pony p) {
		Pony pony = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poney", "id", id));;
		pony.setName(p.getName());
		pony.setWeight(p.getWeight());
		pony.setAge(p.getAge());
		pony.setColor(p.getColor());
		
		Pony ponyUp = repo.save(pony);

		return ponyUp;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePony(@RequestBody Pony p, @PathVariable Long id) {
		repo.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
