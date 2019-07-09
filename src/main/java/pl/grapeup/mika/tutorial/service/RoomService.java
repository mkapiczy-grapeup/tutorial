package pl.grapeup.mika.tutorial.service;

import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;

import java.util.List;

public interface RoomService {
    List<Reservation> getRoomReservations(Long roomId);
    List<Room> getAvailableRoomsForNumberOfGuests(int numberOfGuests);
    Room getRoomForNewReservation(int numberOfGuests);
}
