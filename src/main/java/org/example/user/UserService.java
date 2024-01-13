package org.example.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> {
            log.error("User not found: " + username);
            return new UsernameNotFoundException("User not found " + username);
        });
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }


    public void createNewUser(User user) {
        userRepository.save(user);
        log.info("New user created: " + user.getUsername());
    }
}
