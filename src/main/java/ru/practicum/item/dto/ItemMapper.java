package ru.practicum.item.dto;

import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
@Mapper(componentModel = "spring")
public class ItemMapper {
    public static Item toItem(ItemDto itemDto, User user) {
        Item item = new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user,
                itemDto.getRequest());
        return item;
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequest());
    }

    public static List<ItemDto> toItemDtoList(Iterable<Item> items) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : items) {
            result.add(toItemDto(item));
        }
        return result;
    }
}
