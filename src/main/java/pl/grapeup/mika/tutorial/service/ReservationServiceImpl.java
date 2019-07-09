package pl.grapeup.mika.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.exceptions.NoRoomAvailableException;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomState;
import pl.grapeup.mika.tutorial.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomService roomService;

    @Override
    @Transactional
    public Reservation add(ReservationDTO toAdd) {
        Reservation newReservation = Reservation.fromDTO(toAdd);
        Room room = roomService.getRoomForNewReservation(newReservation.getNumberOfPeople());
        if (room != null) {
            room.setState(RoomState.OCCUPIED);
            newReservation.setRoom(room);
            return reservationRepository.save(newReservation);
        } else {
            throw new NoRoomAvailableException();
        }

    }

    @Override
    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        return optionalReservation != null && optionalReservation.isPresent() ? optionalReservation.get() : null;
    }

    @Override
    public List<Reservation> getByRoom(Long roomId) {
        return roomService.getRoomReservations(roomId);
    }

    @Override
    public boolean validateNewReservation(ReservationDTO reservation) {
        return reservation.getStartDate().isBefore(reservation.getEndDate());
    }
}
