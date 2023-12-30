package ru.practicum.user;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.util.List;

@Transactional(readOnly = true)
interface UserService {
    List<UserDto> getAllUsers();

    @Transactional
    UserDto saveUser(UserDto userDto);


    @Transactional
    void deleteUser(long id);

    User getUserById(long id);
}
