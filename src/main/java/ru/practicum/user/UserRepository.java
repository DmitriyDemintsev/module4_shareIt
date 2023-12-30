package ru.practicum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User save (User user);

    List<User> findAll();

    User getById(long id);

    void deleteById(long id);

}
