package ru.practicum.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.booking.model.Booking;
import ru.practicum.exception.BookingNotFoundException;
import ru.practicum.exception.BookingValidationException;
import ru.practicum.exception.ItemNotFoundException;
import ru.practicum.exception.UserNotFoundException;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Booking create(Booking booking, Long userId, Long itemId) {
        booking.setBooker(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден")));
        booking.setItem(itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item не найден")));
        if (booking.getStart() == null) {
            throw new BookingValidationException("Укажите дату начала бронирования");
        }
        if (booking.getStart().isBefore(LocalDateTime.now())) {
            throw new BookingValidationException("Неверная дата начала бронирования");
        }
        if (booking.getEnd() == null) {
            throw new BookingValidationException("Укажите дату окончания бронирования");
        }
        if (!booking.getEnd().isAfter(booking.getStart())) {
            throw new BookingValidationException("Неверная дата окончания бронирования");
        }
        if (!booking.getItem().getAvailable()) {
            throw new BookingValidationException("Недоступно для бронирования");
        }
        if (booking.getBooker().getId().equals(booking.getItem().getOwner().getId())) {
            throw new BookingNotFoundException("Данная услуга для вас недоступна");
        }
        booking.setStatus(BookingStatus.WAITING);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking update(long id, boolean approved, long userId) {
        Booking old = bookingRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Бронирование не найдено"));
        userRepository.findById(old.getBooker().getId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        itemRepository.findById(old.getItem().getId())
                .orElseThrow(() -> new ItemNotFoundException("Объект бронирования не найден"));
        if (old.getItem().getOwner().getId() != userId) {
            throw new BookingNotFoundException("Данная операция для вас недоступна");
        }
        if (old.getStatus() != BookingStatus.WAITING) {
            throw new BookingValidationException("Данная операция для вас недоступна");
        }
        if (approved == true) {
            old.setStatus(BookingStatus.APPROVED);
        } else {
            old.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(old);
    }

    @Override
    @Transactional
    public void deleteBooking(long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getBookingById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Бронирование не найдено"));

        if (booking.getBooker().getId() != userId && booking.getItem().getOwner().getId() != userId) {
            throw new BookingNotFoundException("Данная операция для вас недоступна");
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookings(long userId, BookingStatusForFilter state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Sort sortByStartDesc = Sort.by(Sort.Direction.DESC, "start");
        Sort sortByStartAsc = Sort.by(Sort.Direction.ASC, "start");
        LocalDateTime now = LocalDateTime.now();
        if (state == BookingStatusForFilter.ALL) {
            return bookingRepository.findByBooker(user, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.FUTURE) {
            return bookingRepository.findByBookerAndStartIsAfter(user, now, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.PAST) {
            return bookingRepository.findByBookerAndEndIsBefore(user, now, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.CURRENT) {
            return bookingRepository.findByBookerAndStartIsBeforeAndEndIsAfter(user, now, now, sortByStartAsc);
        }
        if (state == BookingStatusForFilter.WAITING) {
            return bookingRepository.findByBookerAndStatus(user, BookingStatus.valueOf(state.name()),
                    sortByStartDesc);
        }
        if (state == BookingStatusForFilter.REJECTED) {
            return bookingRepository.findByBookerAndStatus(user, BookingStatus.valueOf(state.name()),
                    sortByStartDesc);
        }
        return bookingRepository.findByBookerAndStatus(user, BookingStatus.valueOf(state.name()), sortByStartDesc);
    }

    @Override
    public List<Booking> getBookingsAllItemsForUser(long userId, BookingStatusForFilter state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        Sort sortByStartDesc = Sort.by(Sort.Direction.DESC, "start");
        Sort sortByStartAsc = Sort.by(Sort.Direction.ASC, "start");
        LocalDateTime now = LocalDateTime.now();
        if (state == BookingStatusForFilter.ALL) {
            return bookingRepository.getBookingsAllItemsForUserWitchStatus(user, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.FUTURE) {
            return bookingRepository.findFutureBookings(user, now, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.PAST) {
            return bookingRepository.findCompletedBookings(user, now, sortByStartDesc);
        }
        if (state == BookingStatusForFilter.CURRENT) {
            return bookingRepository.findCurrentBookings(user, now, now, sortByStartAsc);
        }
        if (state == BookingStatusForFilter.WAITING) {
            return bookingRepository.getBookingsAllItemsForUserWitchStatus(user, BookingStatus.valueOf(state.name()),
                    sortByStartDesc);
        }
        if (state == BookingStatusForFilter.REJECTED) {
            return bookingRepository.getBookingsAllItemsForUserWitchStatus(user, BookingStatus.valueOf(state.name()),
                    sortByStartDesc);
        }
        return bookingRepository.getBookingsAllItemsForUserWitchStatus(user, BookingStatus.valueOf(state.name()),
                sortByStartDesc);
    }

    @Override
    public Booking getLast(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Данный запрос вам недоступен"));
        Sort sortByEnd = Sort.by(Sort.Direction.DESC, "end").and(Sort.by(Sort.Direction.ASC, "id"));
        List<Booking> bookings = bookingRepository.findByItemAndEndIsBeforeAndStatus(item, LocalDateTime.now(), BookingStatus.APPROVED, sortByEnd);
        if (bookings.isEmpty()) {
            return null;
        }
        return bookings.get(0);
    }

    @Override
    public Booking getNext(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Данный запрос вам недоступен"));
        Sort sortByStart = Sort.by(Sort.Direction.ASC, "start").and(Sort.by(Sort.Direction.ASC, "id"));
        List<Booking> bookings = bookingRepository.findByItemAndStartIsAfterAndStatus(item, LocalDateTime.now(), BookingStatus.APPROVED, sortByStart);
        if (bookings.isEmpty()) {
            return null;
        }
        return bookings.get(0);
    }
}
