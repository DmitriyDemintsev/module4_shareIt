package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserCreateDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserMapper;
import ru.practicum.user.dto.UserUpdateDto;

import javax.validation.Valid;
import java.util.List;

@Component
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userMapper.toUserDtoList(userService.getAllUsers());
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userMapper.toUserDto(userService.createUser(userMapper.toUser(userCreateDto)));
    }

    @PatchMapping("/{id}")
    public UserDto update(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable("id") long id) {
        return userMapper.toUserDto(userService.updateUser(userMapper.toUser(userUpdateDto, id)));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public UserDto getUserDtoById (@PathVariable long id) {
            return userMapper.toUserDto(userService.getUserById(id));
    }
}
