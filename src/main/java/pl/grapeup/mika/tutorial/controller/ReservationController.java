package pl.grapeup.mika.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.service.ReservationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public List<ReservationDTO> getAll() {
     return createDTOs(reservationService.getAll());
    }

    @RequestMapping(method= RequestMethod.POST)
    @ResponseBody
    public Reservation create(@RequestBody ReservationDTO reservation) {
        return reservationService.add(reservation);
    }

    @RequestMapping(method= RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@Valid @RequestBody ReservationDTO reservation) {
        reservationService.delete(reservation);
    }

    private List<ReservationDTO> createDTOs(List<Reservation> reservations) {
        List<ReservationDTO> dtos = new ArrayList<>();

        for (Reservation model: reservations) {
            dtos.add(createDTO(model));
        }

        return dtos;
    }

    private ReservationDTO createDTO(Reservation model) {
        ReservationDTO dto =
                ReservationDTO.builder().name(model.getName()).numberOfPeople(model.getNumberOfPeople()).startDate(model.getStartDate()).endDate(model.getEndDate()).build();
        return dto;
    }
}
