package ru.practicum.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //идентификатор вещи
    @NotBlank
    private String name; //название вещи
    @NotBlank
    private String description; //описание вещи
    private Boolean available; //доступность вещи для аренды
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner; //владелец вещи (тот, кто создал её)
    private Long request; //ссылка на запрос user'а, для которого была создана вещь

}
