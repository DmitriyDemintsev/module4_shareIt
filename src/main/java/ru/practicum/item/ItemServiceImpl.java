package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAllItems();
    }

    @Override
    public List<Item> getItems(long userId) {
        return itemRepository.findItemsByUserId(userId);
    }

    @Override
    public Item createItem(Item item) {
        userRepository.getUserById(item.getUserId());
        return itemRepository.createItem(item);
    }

    @Override
    public Item updateItem(Item item) {
        Item old = itemRepository.getItemById(item.getId());
        if (item.getUserId() == null) {
            item.setUserId(old.getUserId());
        }
        if (item.getUrl() == null) {
            item.setUrl(old.getUrl());
        }
        if (item.getName() == null) {
            item.setName(old.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(old.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(old.getAvailable());
        }
        if (item.getOwner() == null) {
            item.setOwner(old.getOwner());
        }
        if (item.getRequest() == null) {
            item.setRequest(old.getRequest());
        }
        return itemRepository.updateItem(item);
    }

    @Override
    public void deleteItem(long itemId) {
        itemRepository.deleteByItemId(itemId);
    }

    @Override
    public Item getItemById(long id) {
        return itemRepository.getItemById(id);
    }

    public List<Item> getItemsBySearch(String query) {
        return itemRepository.getItemsBySearch(query);
    }
}
