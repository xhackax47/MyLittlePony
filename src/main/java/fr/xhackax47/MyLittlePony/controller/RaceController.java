package fr.xhackax47.MyLittlePony.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xhackax47.MyLittlePony.dao.RaceRepository;
import fr.xhackax47.MyLittlePony.exception.ResourceNotFoundException;
import fr.xhackax47.MyLittlePony.models.Race;

@RestController
@RequestMapping("/api/races")
public class RaceController {
	
	@Autowired
	private RaceRepository repo;
	
	@GetMapping("/") 
	public Collection<Race> getAllRaces() {
		return (Collection<Race>) repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Race getRaceById(@PathVariable Long id) {
		Race r = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
		System.out.println(r.getDate());
		
		return r;
	}
	
	@PostMapping("/")
	public Race addRace(@RequestBody Race r) {
		return repo.save(r);
	}
	
	@PutMapping("/{id}")
	public Race updateRace(@PathVariable(value="id") Long id, @Valid @RequestBody Race r) {
		Race race = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
		race.setLocation(r.getLocation());
		race.setDate(r.getDate());
		race.setPonies(r.getPonies());
		Race raceUp = repo.save(race);
		return raceUp;
	}
	
	@DeleteMapping("/{id}")
	public void deleteRace(@RequestBody Race r, @PathVariable Long id) {
		repo.deleteById(id);
	}
}

