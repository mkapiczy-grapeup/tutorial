package pl.grapeup.mika.tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.tags.form.OptionsTag;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.repository.ReservationRepository;
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
}
