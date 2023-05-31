package ru.vorobyov.socialmediaapi.service.database;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of User service.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getCurrentUser() throws EntityNotFoundException {
        String email = getCurrentUserEmail();
        return findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    private String getCurrentUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getPrincipal() instanceof String)
                return (String) authentication.getPrincipal();
        }
        return "";
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> add(User user) {
        return Optional.of(userRepository.save(user));
    }
}
