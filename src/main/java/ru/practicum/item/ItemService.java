package ru.practicum.item;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {

    List<ItemDto> getItems(long userId);

    @Transactional
    ItemDto addNewItem(long userId, ItemDto itemDto);

    @Transactional
    void deleteItem(long itemId);

    List<Item> findAllItems();

    Item getItemById(long id);

    List<Item> getItemsBySearch(String query);
}
