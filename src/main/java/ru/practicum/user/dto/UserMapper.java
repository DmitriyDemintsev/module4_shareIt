package ru.practicum.user.dto;

import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Mapper(componentModel = "spring")
public class UserMapper {
    public static User toUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getName());
        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName());
        return userDto;
    }

    public static List<UserDto> toUserDtoList(Iterable<User> users) {
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(toUserDto(user));
        }
        return result;
    }

//    public User toUser(UserCreateDto userCreateDto) {
//        User user = new User(
//                null,
//                userCreateDto.getEmail(),
//                userCreateDto.getName());
//        return user;
//    }
//
//    public User toUser(UserUpdateDto userUpdateDto, long id) {
//        User user = new User(
//                id,
//                userUpdateDto.getEmail(),
//                userUpdateDto.getName());
//        return user;
//    }
}
