package fr.xhackax47.MyLittlePony.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.xhackax47.MyLittlePony.models.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {}
