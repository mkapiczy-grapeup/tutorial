package pl.grapeup.mika.tutorial.service;

import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<Reservation> add(ReservationDTO newReservation);

    void delete(ReservationDTO reservation);

    List<Reservation> getAll();

}
