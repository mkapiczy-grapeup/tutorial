package pl.grapeup.mika.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.exceptions.InvalidReservationRequestException;
import pl.grapeup.mika.tutorial.exceptions.ReservationNotFoundException;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<ReservationDTO> getAll() {
        return ReservationDTO.createDTOs(reservationService.getAll());
    }

    @GetMapping(value = "/{reservationId}")
    public ReservationDTO getById(@PathVariable Long reservationId) {
        Reservation reservation =  reservationService.getById(reservationId);
        if(reservation != null){
            return ReservationDTO.createDTO(reservation);
        } else {
            throw new ReservationNotFoundException();
        }
    }

    @GetMapping(params = "roomId")
    public List<ReservationDTO> getByRoomId(@RequestParam Long roomId) {
        return ReservationDTO.createDTOs(reservationService.getByRoom(roomId));
    }

    @PostMapping
    public ReservationDTO create(@RequestBody ReservationDTO reservation) {
        if (reservationService.validateNewReservation(reservation)) {
            return ReservationDTO.createDTO(reservationService.add(reservation));
        } else {
            throw new InvalidReservationRequestException();
        }
    }

    @DeleteMapping(value ="/{reservationId}")
    public void delete(@PathVariable Long reservationId) {
        reservationService.delete(reservationId);
    }
}
