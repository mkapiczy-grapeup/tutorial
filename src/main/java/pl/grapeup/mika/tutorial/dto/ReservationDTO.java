package pl.grapeup.mika.tutorial.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class ReservationDTO {

    private Long id;
    @NotBlank
    private String name;
    @Positive
    private Integer numberOfPeople;
    @FutureOrPresent
    @NotEmpty
    private LocalDate startData;
    @Future
    @NotEmpty
    private LocalDate endDate;
}
