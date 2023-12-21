package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.ItemCreateDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemMapper;
import ru.practicum.item.dto.ItemUpdateDto;
import ru.practicum.item.model.Item;

import javax.validation.Valid;
import java.util.List;

@Component
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @GetMapping
    public List<Item> get(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItems(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getItemDtoById(@PathVariable long id) {
        return itemMapper.toItemDto(itemService.getItemById(id));
    }

    @GetMapping("/search")
    public List<Item> getSearchItem(@RequestParam("text") String query) {
        return itemService.getItemsBySearch(query);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemMapper.toItemDto(itemService.createItem(itemMapper.toItem(itemCreateDto, userId)));
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @Valid @RequestBody ItemUpdateDto itemUpdateDto, @PathVariable("id") long id) {
        return itemMapper.toItemDto(itemService.updateItem(itemMapper.toItem(itemUpdateDto, id, userId)));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable("itemId") long itemId) {
        itemService.deleteItem(itemId);
    }
}
