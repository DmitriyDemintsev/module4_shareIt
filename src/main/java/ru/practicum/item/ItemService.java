package ru.practicum.item;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.model.Item;

import java.util.List;

@Transactional(readOnly = true)
public interface ItemService {

    List<Item> getItems(long userId);

    @Transactional
    Item create(Long userId, Item item);

    @Transactional
    Item update(Long userId, Item item);

    @Transactional
    void deleteItem(long itemId);

    List<Item> findAllItems();

    Item getItemById(long id);

    List<Item> getItemsBySearch(String query);
}
