package fr.xhackax47.MyLittlePony.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.xhackax47.MyLittlePony.dao.UserRepository;
import fr.xhackax47.MyLittlePony.models.User;
import fr.xhackax47.MyLittlePony.security.UserPrincipal;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
    private final UserRepository userRepository;
	
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);
        User user = userRepository.findUserWithName(username).orElseThrow(() -> new UsernameNotFoundException("Utilisateur inexistant"));
        return user;
    }
    
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User non trouvé avec l'id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
