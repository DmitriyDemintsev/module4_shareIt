//package ru.practicum.item.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import ru.practicum.user.model.User;
//
//import javax.validation.constraints.NotBlank;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ItemUpdateDto {
//    private Long userId; //идентификатор user'а, который создал вещь
//    private String name; //название вещи
//    @NotBlank
//    private String description; //описание вещи
//    private Boolean available; //доступность вещи для аренды
//    private User owner; //владелец вещи (тот, кто создал её)
//    private Long request; //ссылка на запрос user'а, для которого была создана вещь
//}
