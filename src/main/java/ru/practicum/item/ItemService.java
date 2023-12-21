package ru.practicum.item;

import ru.practicum.item.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> getItems(long userId);

    Item createItem(Item item);

    Item updateItem(Item item);

    void deleteItem(long itemId);

    List<Item> findAllItems();

    Item getItemById(long id);

    List<Item> getItemsBySearch(String query);
}
