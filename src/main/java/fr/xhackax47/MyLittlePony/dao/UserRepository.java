package fr.xhackax47.MyLittlePony.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.xhackax47.MyLittlePony.models.User;

public interface UserRepository extends JpaRepository<User, Long>{}
