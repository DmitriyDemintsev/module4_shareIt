package ru.practicum.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item save(Item item);

    List<Item> findByOwner(long userId);

    void deleteById(long itemId);

    Item getItemById(long id);

    List<Item> findAll();

    @Query(" select i from Item i " +
            "where upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%'))")
    List<Item> getItemsBySearch(String query);
}
