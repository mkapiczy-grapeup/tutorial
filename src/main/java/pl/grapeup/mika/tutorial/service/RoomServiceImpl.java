package pl.grapeup.mika.tutorial.service;

import org.springframework.stereotype.Service;
import pl.grapeup.mika.tutorial.exceptions.NoRoomAvailableException;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomClass;
import pl.grapeup.mika.tutorial.model.RoomState;
import pl.grapeup.mika.tutorial.repository.RoomRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;

    public RoomServiceImpl(RoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Reservation> getRoomReservations(Long roomId) {
        return repository.findById(roomId).map(Room::getReservations).orElse(new ArrayList<>());
    }

    @Override
    public List<Room> getAvailableRoomsForNumberOfGuests(int numberOfGuests) {
        List<Room> availableRooms = new ArrayList<>();
        List<RoomClass> roomClasses = getAllRoomClassesByNumberOfGuests(numberOfGuests);
        for (RoomClass singleRoomClass : roomClasses) {
            availableRooms.addAll(getAvailableRoomsByRoomClass(singleRoomClass));
        }
        return availableRooms;
    }

    @Override
    public Room getRoomForNewReservation(int numberOfGuests) throws NoRoomAvailableException {
        return Optional.ofNullable(getAvailableRoomsForNumberOfGuests(numberOfGuests)).stream().flatMap(Collection::stream).findFirst().orElseThrow(NoRoomAvailableException::new);
    }

    private List<RoomClass> getAllRoomClassesByNumberOfGuests(int numberOfGuests) {
        return repository.getAllRoomClassesWithSizeSmallerThanNumberOfGuests(numberOfGuests);
    }

    private List<Room> getAvailableRoomsByRoomClass(RoomClass roomClass) {
        return repository.getByRoomClassAndState(roomClass, RoomState.AVAILABLE);
    }
}
