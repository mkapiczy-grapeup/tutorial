package pl.grapeup.mika.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.grapeup.mika.tutorial.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
