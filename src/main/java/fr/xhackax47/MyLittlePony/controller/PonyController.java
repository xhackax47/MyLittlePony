package fr.xhackax47.MyLittlePony.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.xhackax47.MyLittlePony.dao.PonyRepository;
import fr.xhackax47.MyLittlePony.dao.RaceRepository;
import fr.xhackax47.MyLittlePony.exception.ResourceNotFoundException;
import fr.xhackax47.MyLittlePony.models.Pony;
import fr.xhackax47.MyLittlePony.models.Race;

@CrossOrigin
@RestController
@RequestMapping("/api/ponies")
public class PonyController {
	
	@Autowired
	private PonyRepository ponyRepo;
	
	@Autowired
	private RaceRepository raceRepo;
	

    @CrossOrigin(origins = "*")
	@GetMapping("/") 
	public Collection<Pony> getAllPonies() {
		return (Collection<Pony>) ponyRepo.findAll();
	}
	
    @CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public Optional<Pony> getPonyById(@PathVariable Long id) {
		return ponyRepo.findById(id);
	}
	
    @CrossOrigin(origins = "*")
	@PostMapping("/")
	public Pony addPony(@RequestBody Pony p) {
		return ponyRepo.save(p);
	}
	
    @CrossOrigin(origins = "*")
	@PutMapping("/{id}")
	public Pony updatePony(@PathVariable(value="id") Long id, @Valid @RequestBody Pony p) {
		Pony pony = ponyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poney", "id", id));
		pony.setName(p.getName());
		pony.setWeight(p.getWeight());
		pony.setAge(p.getAge());
		pony.setColor(p.getColor());
		
		Pony ponyUp = ponyRepo.save(pony);

		return ponyUp;
	}
	
    @CrossOrigin(origins = "*")
    @OnDelete(action = OnDeleteAction.CASCADE)
	@DeleteMapping("/{id}")
	public void deletePony(@PathVariable long id) {
    	// Supprimer Pony dans présent dans une course
		Optional<Pony> ponyFound = ponyRepo.findById(id);
		if(ponyFound.isPresent()) {
			ArrayList<Race> listRaces = (ArrayList<Race>) raceRepo.findAll();
			listRaces.forEach((race) -> {
				List<Pony> filteredPonies = race.getPonies().stream().filter((pony) -> {
					return pony.getId() != id;
				}).collect(Collectors.toList());
				race.setPonies(filteredPonies);
				raceRepo.save(race);
			});
		}
    	ponyRepo.deleteById(id);
	}
}
