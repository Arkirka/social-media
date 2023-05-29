package ru.vorobyov.socialmediaapi.service.database;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.vorobyov.socialmediaapi.entity.User;
import ru.vorobyov.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> add(User user) {
        return Optional.of(userRepository.save(user));
    }
}
