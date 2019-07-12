package pl.grapeup.mika.tutorial.controller;

import org.springframework.web.bind.annotation.*;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.exceptions.ReservationNotFoundException;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.service.ReservationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

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
    public ReservationDTO create(@Valid @RequestBody ReservationDTO reservation) {
        return ReservationDTO.createDTO(reservationService.add(reservation));
    }

    @DeleteMapping(value ="/{reservationId}")
    public void delete(@PathVariable Long reservationId) {
        reservationService.delete(reservationId);
    }
}
