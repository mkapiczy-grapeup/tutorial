package pl.grapeup.mika.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomClass;
import pl.grapeup.mika.tutorial.model.RoomState;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM RoomClass r WHERE r.size >= :numberOfGuests")
    List<RoomClass> getAllRoomClassesWithSizeSmallerThanNumberOfGuests(int numberOfGuests);

    List<Room> getByRoomClassAndState(RoomClass roomClass, RoomState state);
}
