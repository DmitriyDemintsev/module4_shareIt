package ru.practicum.booking;

public enum BookingStatusForFilter {
    ALL, //статус по умолчанию для запроса Filter
    CURRENT, //текущие
    PAST, //завершённые
    FUTURE, //будущие
    WAITING, //ожидающие подтверждения
    REJECTED //отклонённые
}
