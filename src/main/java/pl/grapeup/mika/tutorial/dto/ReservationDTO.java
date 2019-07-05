package pl.grapeup.mika.tutorial.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

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
}
