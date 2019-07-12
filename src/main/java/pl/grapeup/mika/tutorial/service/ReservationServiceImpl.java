package pl.grapeup.mika.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.exceptions.ReservationNotFoundException;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomState;
import pl.grapeup.mika.tutorial.repository.ReservationRepository;
import pl.grapeup.mika.tutorial.repository.RoomRepository;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomRepository roomRepository;

    private final RoomService roomService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    @Override
    @Transactional
    public Reservation add(ReservationDTO toAdd) {
        Reservation newReservation = Reservation.fromDTO(toAdd);
        Room room = roomService.getRoomForNewReservation(newReservation.getNumberOfPeople());
        room.setState(RoomState.OCCUPIED);
        newReservation.setRoom(room);
        return reservationRepository.save(newReservation);
    }

    @Override
    public void delete(Long reservationId) {
        Reservation reservationToDelete = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Room room = reservationToDelete.getRoom();
        if (room != null) {
            room.setState(RoomState.AVAILABLE);
            roomRepository.save(room);
        }
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> getByRoom(Long roomId) {
        return roomService.getRoomReservations(roomId);
    }
}
