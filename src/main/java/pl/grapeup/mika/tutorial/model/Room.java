package pl.grapeup.mika.tutorial.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_roomClass")
    private RoomClass roomClass;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private List<Reservation> reservations;
}
