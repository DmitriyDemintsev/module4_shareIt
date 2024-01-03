package ru.practicum.item;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item save(Item item); //создать и обновить
    void deleteById(long itemId); //удалить

    List<Item> findByOwner(User userOwner, Sort sort); //все вещи владельца по владельцу

    Item getItemById(long id); //вещь по её id

    List<Item> findAll(); // вообще все вещи

    @Query(" select i from Item i join i.owner as u " +
            "where (upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))) " +
            "and available = true")
    List<Item> findAllBySearch(String query); //для поиска по запросу
}
