package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDto {
    private Long userId; //идентификатор user'а, который создал вещь
    private String url;
    private String name;
    private String description;
    private Boolean available; //доступность вещи для аренды
    private String owner; //владелец вещи (тот, кто создал её)
    private Long request; //ссылка на запрос user'а, для которого была создана вещь
}
