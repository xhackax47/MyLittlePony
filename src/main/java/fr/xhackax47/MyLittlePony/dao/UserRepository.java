package fr.xhackax47.MyLittlePony.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.xhackax47.MyLittlePony.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query(" select u from User u " + " where u.username = ?1")
	Optional<User> findUserWithName(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    List<User> findByIdIn(List<Long> userIds);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
