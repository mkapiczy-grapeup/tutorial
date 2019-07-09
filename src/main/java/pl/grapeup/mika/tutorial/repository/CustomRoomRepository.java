package pl.grapeup.mika.tutorial.repository;

import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomClass;

import java.util.List;

public interface CustomRoomRepository {
    List<Room> getAvailableRoomsForRoomClass(RoomClass roomClass);
    List<RoomClass> getAllRoomClassesForNumberOfGuests(int numberOfGuests);

}
