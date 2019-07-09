package pl.grapeup.mika.tutorial.service;

import pl.grapeup.mika.tutorial.model.Reservation;

import java.util.List;

public interface RoomService {
    List<Reservation> getRoomReservations(Long roomId);
}
