package fr.xhackax47.MyLittlePony.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.xhackax47.MyLittlePony.models.Role;
import fr.xhackax47.MyLittlePony.models.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}