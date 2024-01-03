package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.booking.dto.BookingDto;
import ru.practicum.item.model.Comment;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemDto implements Serializable {
    private Long id; //идентификатор вещи
    private String name;
    private String description;
    private Boolean available; //доступность вещи для аренды
    private Long owner;
    private BookingDto nextBooking;
    private BookingDto lastBooking;
    private List<CommentDto> comments;
//    private Long request; //ссылка на запрос user'а, для которого была создана вещь
}
