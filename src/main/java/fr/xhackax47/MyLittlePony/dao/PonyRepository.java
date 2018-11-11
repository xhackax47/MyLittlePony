package fr.xhackax47.MyLittlePony.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.xhackax47.MyLittlePony.models.Pony;

@Repository
public interface PonyRepository extends JpaRepository<Pony, Long> {}