package ru.practicum.user;

import ru.practicum.user.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User createUser(User user);

    User updateUser(User user);

    User getUserById(long id);

    void deleteUser(long id);
}
