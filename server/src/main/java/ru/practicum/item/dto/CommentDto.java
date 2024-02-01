package ru.practicum.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.item.model.Item;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id; // идентификатор комментария
    private String text; //содержимое комментария
    private ItemDto item; //вещь, к которой относится комментарий
    private UserDto author; //автор комментария - тот, кто пользовался вещью
    private String authorName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime created; //дата создания комментария
}
