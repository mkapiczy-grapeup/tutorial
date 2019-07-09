package pl.grapeup.mika.tutorial.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_roomClass")
    private RoomClass roomClass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Reservation> reservations;

    @Enumerated(EnumType.STRING)
    private RoomState state;

    private Long roomNumber;
}
