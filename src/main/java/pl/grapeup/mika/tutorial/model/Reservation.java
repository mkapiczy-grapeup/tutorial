package pl.grapeup.mika.tutorial.model;

import lombok.Builder;
import lombok.Data;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer numberOfPeople;
    private LocalDate startDate;
    private LocalDate endDate;

    public static Reservation fromDTO(ReservationDTO dto) {
       return Reservation.builder().name(dto.getName()).numberOfPeople(dto.getNumberOfPeople()).startDate(dto.getStartDate()).endDate(dto.getEndDate()).build();
    }
}
