package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentCreateDto implements Serializable {
    private Long id; //идентификатор комментария
    private String text; //содержимое комментария
}
