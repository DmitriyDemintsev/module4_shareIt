package ru.practicum.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking save(Booking booking); //создать/обновить

    void deleteById(long id); //удалить

    List<Booking> findAll(); //показать все бронирования

    Booking getById(long id); //найти бронирование по его id

    List<Booking> findByBookerAndStatus(User booker, BookingStatus status, Sort sort); //бронирования по арендатору и статусу

    List<Booking> findByBooker(User booker, Sort sort); //бронирования по арендатору

    List<Booking> findByBookerAndEndIsBefore(User booker, LocalDateTime end, Sort sort); //завершенные заказы

    List<Booking> findByBookerAndEndIsBeforeAndItem(User booker, LocalDateTime end, Item item); //все завершенные
                                                                                        // заказы для вещи и заказчика

    List<Booking> findByBookerAndStartIsAfter(User booker, LocalDateTime start, Sort sort); //будущие заказы

    List<Booking> findByItemAndEndIsBeforeAndStatus(Item item, LocalDateTime end, BookingStatus status, Sort sort); //заказы по времени окончания

    List<Booking> findByItemAndStartIsAfterAndStatus(Item item, LocalDateTime end, BookingStatus status, Sort sort); //заказы по времени окончания

    List<Booking> findByBookerAndStartIsBeforeAndEndIsAfter(User booker, LocalDateTime start,
                                                            LocalDateTime end, Sort sort); //текущие заказы

    @Query("select b from Booking b join b.item as i " +
            "where i.owner = ?1 and b.status = ?2")
    List<Booking> getBookingsAllItemsForUserWitchStatus(User booker, BookingStatus state, Sort sort); //все заказы со стасутом

    @Query("select b from Booking b join b.item as i " +
            "where i.owner = ?1")
    List<Booking> getBookingsAllItemsForUserWitchStatus(User booker, Sort sort); //вообще все заказы

    @Query("select b from Booking b join b.item as i " +
            "where i.owner = ?1 and b.end < ?2")
    List<Booking> findCompletedBookings(User booker, LocalDateTime end, Sort sort); //завершенные заказы

    @Query("select b from Booking b join b.item as i " +
            "where i.owner = ?1 and b.start > ?2")
    List<Booking> findFutureBookings(User booker, LocalDateTime start, Sort sort); //будущие заказы

    @Query("select b from Booking b join b.item as i " +
            "where i.owner = ?1 and b.start < ?2 and b.end > ?3")
    List<Booking> findCurrentBookings(User booker, LocalDateTime start, LocalDateTime end, Sort sort); //текущие заказы
}
