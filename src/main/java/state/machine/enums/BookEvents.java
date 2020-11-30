package state.machine.enums;

public enum BookEvents {
    BORROW, /*книга занята*/
    RETURN, /*книга возвращается*/
    START_REPAIR, /*начато восставновление книги*/
    END_REPAIR /*окончено восставновление книги*/
}
