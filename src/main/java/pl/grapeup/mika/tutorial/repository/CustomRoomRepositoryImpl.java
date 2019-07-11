package pl.grapeup.mika.tutorial.repository;

import org.springframework.beans.factory.annotation.Autowired;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.model.RoomClass;
import pl.grapeup.mika.tutorial.model.RoomState;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomRoomRepositoryImpl implements CustomRoomRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Room> getAvailableRoomsForRoomClass(RoomClass roomClass) {
        TypedQuery<Room> q = entityManager.createQuery("SELECT r FROM Room r WHERE r.roomClass = :roomClass AND r.state = :availableState",
                Room.class);
        q.setParameter("roomClass", roomClass);
        q.setParameter("availableState", RoomState.AVAILABLE);
        List<Room> results = q.getResultList();
        return results;
    }

    @Override
    public List<RoomClass> getAllRoomClassesForNumberOfGuests(int numberOfGuests) {
        TypedQuery<RoomClass> q = entityManager.createQuery("SELECT r FROM RoomClass r WHERE r.size >= :numberOfGuests",
                RoomClass.class);
        q.setParameter("numberOfGuests", numberOfGuests);
        List<RoomClass> results = q.getResultList();
        return results;
    }
}
