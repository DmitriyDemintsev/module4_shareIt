package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ItemDto implements Serializable {
    private Long id; //идентификатор вещи
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available; //доступность вещи для аренды
    private Long owner;
    private Long request; //ссылка на запрос user'а, для которого была создана вещь
}
