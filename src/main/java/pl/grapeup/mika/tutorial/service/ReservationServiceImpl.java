package pl.grapeup.mika.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Override
    public Optional<Reservation> add(ReservationDTO toAdd) {
        if(validateNewReservation(toAdd))
            return Optional.of(repository.save(Reservation.fromDTO(toAdd)));
        return Optional.empty();
    }

    @Override
    public void delete(ReservationDTO toDelete) {
        repository.delete(Reservation.fromDTO(toDelete));
    }

    @Override
    public List<Reservation> getAll() {
        return repository.findAll();
    }

    private boolean validateNewReservation(ReservationDTO reservation) {
        return reservation.getStartData().isBefore(reservation.getEndDate());
    }
}
