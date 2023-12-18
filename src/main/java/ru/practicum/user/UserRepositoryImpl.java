package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.exception.UserAlreadyExistException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    protected long idGeneratorForUser = 0L;

    private long generateIdForUser() {
        return ++idGeneratorForUser;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        for (User userCheck : users.values()) {
            if (user.getEmail().equals(userCheck.getEmail())) {
                throw new UserAlreadyExistException("Пользователь с таким email " + user.getEmail() +
                        " уже существует");
            }
        }
        user.setId(generateIdForUser());
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Создан новый пользователь с электронной почтой " + user.getEmail());
        } else {
            log.debug("Передаваемый id " + user.getId() + " уже есть в списке");
            throw new UserAlreadyExistException("Пользователь с таким id " + user.getId()
                    + " уже есть в списке");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        for (User userCheck : users.values()) {
            if (user.getEmail().equals(userCheck.getEmail()) && user.getId() != userCheck.getId()) {
                throw new UserAlreadyExistException("Указанная электронная почта " + user.getEmail() +
                        " принадлежит другому пользователю");
            }
        }
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Обновлены данные пользователя с электронной почтой " + user.getEmail());
        } else {
            log.debug("Невозможно обновить данные, пользователь с id " + user.getId()
                    + " отсутствует в списке пользователей");
            throw new UserNotFoundException("Пользователь с таким id " + user.getId()
                    + " отсутствует в списке");
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        users.remove(id);
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь с таким id " + id + " не найден");
        }
        return users.get(id);
    }
}
