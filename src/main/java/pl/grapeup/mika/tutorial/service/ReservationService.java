package pl.grapeup.mika.tutorial.service;

import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation add(ReservationDTO newReservation);

    void delete(ReservationDTO reservation);

    List<Reservation> getAll();

}
