package pl.grapeup.mika.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grapeup.mika.tutorial.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
