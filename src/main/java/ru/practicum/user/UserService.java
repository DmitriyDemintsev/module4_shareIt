package ru.practicum.user;

import ru.practicum.user.model.User;

import java.util.List;

interface UserService {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(long id);

    User getUserById(long id);
}
