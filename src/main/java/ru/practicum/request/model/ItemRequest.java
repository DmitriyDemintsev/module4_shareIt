package ru.practicum.request.model;

import lombok.Data;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
public class ItemRequest {
    private Long id;
    private String description; //описание для запрашиваемой вещи
    private User requestor; //автор запроса
    private LocalDateTime created; //дата и время создания запроса
}
