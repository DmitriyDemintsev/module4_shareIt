package ru.practicum.booking;

import lombok.Data;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Data
public class Booking {
    private Long id;
    private LocalDateTime start; //дата и время начала бронирование
    private LocalDateTime end; //даьа и время окончания бронирования
    private Item item; //что бронируют
    private User booker; //кто бронирует
    private String status; //статус бронирования
}
