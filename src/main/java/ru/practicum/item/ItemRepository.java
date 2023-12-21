package ru.practicum.item;

import ru.practicum.item.model.Item;

import java.util.List;

interface ItemRepository {

    List<Item> findItemsByUserId(long userId);

    Item createItem(Item item);

    void deleteByItemId(long itemId);

    Item getItemById(long id);

    Item updateItem(Item item);

    List<Item> findAllItems();


    List<Item> getItemsBySearch(String query);
}
