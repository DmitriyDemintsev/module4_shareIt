package ru.practicum.item.dto;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.item.model.Item;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public class ItemMapper {
    public Item toItem(ItemDto itemDto) {
        Item item = new Item(
                itemDto.getId(),
                itemDto.getUserId(),
                itemDto.getUrl(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                itemDto.getOwner(),
                itemDto.getRequest());
        return item;
    }

    public Item toItem(ItemCreateDto itemCreateDto, long userId) {
        Item item = new Item(
                null,
                userId,
                itemCreateDto.getUrl(),
                itemCreateDto.getName(),
                itemCreateDto.getDescription(),
                itemCreateDto.getAvailable(),
                itemCreateDto.getOwner(),
                itemCreateDto.getRequest());
        return item;
    }

    public Item toItem(ItemUpdateDto itemUpdateDto, long id, long userId) {
        Item item = new Item(
                id,
                userId,
                itemUpdateDto.getUrl(),
                itemUpdateDto.getName(),
                itemUpdateDto.getDescription(),
                itemUpdateDto.getAvailable(),
                itemUpdateDto.getOwner(),
                itemUpdateDto.getRequest());
        return item;
    }

    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getUserId(),
                item.getUrl(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                item.getRequest());
    }

    public List<ItemDto> toItemDtoList(Iterable<Item> items) {
        List<ItemDto> result = new ArrayList<>();
        for (Item item : items) {
            result.add(toItemDto(item));
        }
        return result;
    }
}
