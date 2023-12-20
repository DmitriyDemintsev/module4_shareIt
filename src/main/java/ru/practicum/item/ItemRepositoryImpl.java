package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.exception.ItemAlreadyExistException;
import ru.practicum.exception.ItemNotFoundException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, List<Item>> userItems = new HashMap<>(); //id пользователя, которому принадлежит вещь
    private final Map<Long, Item> items = new HashMap<>();

    protected long idGeneratorForItem = 0L;

    private long generateIdForItem() {
        return ++idGeneratorForItem;
    }

    @Override
    public List<Item> findItemsByUserId(long userId) {
        if (!userItems.containsKey(userId)) {
            throw new ItemNotFoundException("Не удается выполнит запрос, вещи пользователя не найдены");
        }
        return userItems.get(userId);
    }

    @Override
    public List<Item> findAllItems() {
        return new ArrayList<>(userItems.values().stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList()));
    }

    @Override
    public Item createItem(Item item) {
        if (item.getUserId() == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        item.setId(generateIdForItem());
        items.put(item.getId(), item);
        userItems.compute(item.getUserId(), (userId, itemList) -> {
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            itemList.add(item);
            return itemList;
        });
        return item;
    }

    @Override
    public Item updateItem(Item item) {
        for (Item itemCheck : findItemsByUserId(item.getUserId())) {
            if (!item.getId().equals(itemCheck.getId())) {
                continue;
            }
            if (!item.getUserId().equals(itemCheck.getUserId())) {
                throw new ItemAlreadyExistException("Данная операция для вас недоступна, вы пытаетесь изменить данные " +
                        "не принадлежащей вам вещи");
            }
            itemCheck.setAvailable(item.getAvailable());
            itemCheck.setDescription(item.getDescription());
            itemCheck.setName(item.getName());
            items.put(item.getId(), item);

            return itemCheck;
        }
        throw new ItemNotFoundException("Объект с таким id " + item.getId()
                + " отсутствует в списке");
    }

    @Override
    public void deleteByItemId(long itemId) {
        items.remove(itemId);
        userItems.remove(itemId);
    }

    @Override
    public Item getItemById(long id) {
        for (Long key : items.keySet()) {
            if (key == id) {
                return items.get(id);
            }
        }

        throw new ItemNotFoundException("Объект с таким id " + id + " не найден");
    }

    @Override
    public List<Item> getItemsBySearch(String query) {
        if (query.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> itemsBySearch = new ArrayList<>();
        itemsBySearch = findAllItems().stream()
                .filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(query.toLowerCase()))
                .filter(item -> item.getAvailable())
                .collect(Collectors.toList());
        return itemsBySearch;
    }
}
