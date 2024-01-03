package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ItemAlreadyExistException;
import ru.practicum.exception.ItemNotFoundException;
import ru.practicum.exception.ItemValidationException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.util.ArrayList;
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
    @Transactional
    public Item create(Long userId, Item item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new ItemValidationException("Отсутствует название для item");
        }
        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            throw new ItemValidationException("Отсутствует описание для item");
        }
        if (item.getAvailable() == null) {
            throw new ItemValidationException("Укажите статус для брoнирования");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user не найден"));

        item.setOwner(user);
        item = itemRepository.save(item);
        return item;
    }

    @Override
    @Transactional
    public Item update(Long userId, Item item) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user не найден"));
        Item old = itemRepository.getItemById(item.getId());
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
//        if (item.getRequest() == null) {
//            item.setRequest(old.getRequest());
//        }
        if (item.getName().isEmpty()) {
            throw new ItemValidationException("Отсутствует назание для item");
        }
        if (item.getDescription().isEmpty()) {
            throw new ItemValidationException("Отсутствует описание для item");
        }
        if (!old.getOwner().getId().equals(userId)) {
            throw new ItemAlreadyExistException("Данная операция для вас недоступна, вы пытаетесь изменить данные " +
                    "не принадлежащей вам вещи");
        }
        item.setOwner(user);
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public Item getItemById(long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("item не найден"));
    }

    @Override
    public List<Item> getItems(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user не найден"));
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        return itemRepository.findByOwner(user, sortById);
    }

    public List<Item> getItemsBySearch(String query) {
        List<Item> items = new ArrayList<>();
        if (query.isEmpty()) {
            return items;
        }
        return itemRepository.findAllBySearch(query);
    }
}
