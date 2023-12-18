package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCreateDto {
    private Long userId;
    private String url;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available; //доступность вещи для аренды
    private String owner;
    private Long request; //ссылка на запрос user'а, для которого была создана вещь
}
