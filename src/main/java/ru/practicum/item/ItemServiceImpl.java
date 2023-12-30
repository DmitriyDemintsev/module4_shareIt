package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;
import ru.practicum.item.dto.ItemMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<ItemDto> getItems(long userId) {
        List<Item> userItems = itemRepository.findByOwner(userId);
        return ItemMapper.toItemDtoList(userItems);
    }

    @Override
    @Transactional
    public ItemDto addNewItem(long userId, ItemDto itemDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user не найден"));
        Item item = itemRepository.save(ItemMapper.toItem(itemDto, user));
        return ItemMapper.toItemDto(item);
    }

    @Override
    @Transactional
    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public Item getItemById(long id) {
        return itemRepository.getItemById(id);
    }

    public List<Item> getItemsBySearch(String query) {
        return itemRepository.getItemsBySearch(query);
    }
}
