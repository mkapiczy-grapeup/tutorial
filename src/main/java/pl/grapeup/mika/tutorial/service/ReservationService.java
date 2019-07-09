package pl.grapeup.mika.tutorial.service;

import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation add(ReservationDTO newReservation);

    void delete(Long reservationId);

    List<Reservation> getAll();

    Reservation getById(Long id);

    List<Reservation> getByRoom(Long roomId);

    boolean validateNewReservation(ReservationDTO reservation);

}
