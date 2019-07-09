package pl.grapeup.mika.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.repository.ReservationRepository;
import pl.grapeup.mika.tutorial.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Reservation add(ReservationDTO toAdd) {
        if(validateNewReservation(toAdd))
            return reservationRepository.save(Reservation.fromDTO(toAdd));
        return null;
    }

    @Override
    public void delete(ReservationDTO toDelete) {
        reservationRepository.delete(Reservation.fromDTO(toDelete));
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.getOne(id);
    }

    @Override
    public List<Reservation> getByRoom(Long roomId) {
        return Optional.ofNullable(roomRepository.getOne(roomId)).map(Room::getReservations).orElse(new ArrayList<>());
    }


    private boolean validateNewReservation(ReservationDTO reservation) {
        return reservation.getStartDate().isBefore(reservation.getEndDate());
    }
}
