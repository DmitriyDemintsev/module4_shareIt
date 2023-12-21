package ru.practicum.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Item {
    private Long id; //идентификатор вещи
    private Long userId; //идентификатор user'а, который создал вещь
    private String url;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Boolean available; //доступность вещи для аренды
    private String owner; //владелец вещи (тот, кто создал её)
    private Long request; //ссылка на запрос user'а, для которого была создана вещь
}
