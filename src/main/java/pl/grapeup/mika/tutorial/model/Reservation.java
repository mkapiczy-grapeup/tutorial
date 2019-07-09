package pl.grapeup.mika.tutorial.model;

import lombok.*;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer numberOfPeople;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public static Reservation fromDTO(ReservationDTO dto) {
        return Reservation.builder().name(dto.getName()).numberOfPeople(dto.getNumberOfPeople()).startDate(dto.getStartDate()).endDate(dto.getEndDate()).build();
    }
}
