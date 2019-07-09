package pl.grapeup.mika.tutorial.dto;

import lombok.Builder;
import lombok.Data;
import pl.grapeup.mika.tutorial.model.Reservation;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ReservationDTO {
    @NotEmpty
    private String name;
    @Positive
    private Integer numberOfPeople;
    @FutureOrPresent
    private LocalDate startDate;
    @Future
    private LocalDate endDate;

    public static List<ReservationDTO> createDTOs(List<Reservation> reservations) {
        return reservations.stream().map(r -> createDTO(r)).collect(Collectors.toList());
    }

    public static ReservationDTO createDTO(Reservation model) {
        return ReservationDTO.builder().name(model.getName()).numberOfPeople(model.getNumberOfPeople()).startDate(model.getStartDate()).endDate(model.getEndDate()).build();
    }
}
