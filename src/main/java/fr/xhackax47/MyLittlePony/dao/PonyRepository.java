package fr.xhackax47.MyLittlePony.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.xhackax47.MyLittlePony.models.Pony;

public interface PonyRepository extends JpaRepository<Pony, Long> {}