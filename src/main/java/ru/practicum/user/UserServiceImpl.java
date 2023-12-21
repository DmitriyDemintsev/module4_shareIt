package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        User old = userRepository.getUserById(user.getId());
        if (user.getName() == null) {
            user.setName(old.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(old.getEmail());
        }
        return userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteUser(id);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getUserById(id);
    }
}