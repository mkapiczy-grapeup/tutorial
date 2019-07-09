package pl.grapeup.mika.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomClass;
import pl.grapeup.mika.tutorial.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository repository;

    @Override
    public List<Reservation> getRoomReservations(Long roomId) {
        return Optional.ofNullable(repository.getOne(roomId)).map(Room::getReservations).orElse(new ArrayList<>());
    }

    @Override
    public List<Room> getAvailableRoomsForNumberOfGuests(int numberOfGuests) {
        List<Room> availableRooms = new ArrayList<>();
        List<RoomClass> roomClasses = getAllRoomClassesByNumberOfGuests(numberOfGuests);
        for (RoomClass singleRoomClass : roomClasses) {
            availableRooms.addAll(repository.getAvailableRoomsForRoomClass(singleRoomClass));
        }
        return availableRooms;
    }

    @Override
    public Room getRoomForNewReservation(int numberOfGuests) {
        List<Room> availableRooms = getAvailableRoomsForNumberOfGuests(numberOfGuests);
        return availableRooms != null && !availableRooms.isEmpty() ? availableRooms.get(0) : null;
    }

    private List<RoomClass> getAllRoomClassesByNumberOfGuests(int numberOfGuests) {
        return repository.getAllRoomClassesForNumberOfGuests(numberOfGuests);
    }
}
