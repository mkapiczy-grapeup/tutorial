package pl.grapeup.mika.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.grapeup.mika.tutorial.dto.ReservationDTO;
import pl.grapeup.mika.tutorial.model.Reservation;
import pl.grapeup.mika.tutorial.model.Room;
import pl.grapeup.mika.tutorial.service.ReservationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TutorialApplication.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testReservationGetAll() throws Exception {
        Reservation r1 = Reservation.builder().name("R1").build();
        Reservation r2 = Reservation.builder().name("R2").build();

        when(reservationServiceMock.getAll()).thenReturn(Arrays.asList(r1, r2));

        mockMvc.perform(get("/api/v1/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("R1")))
                .andExpect(jsonPath("$[1].name", is("R2")));

        verify(reservationServiceMock, times(1)).getAll();
        verifyNoMoreInteractions(reservationServiceMock);
    }

    @Test
    public void testReservationGetById() throws Exception {
        Reservation r1 = Reservation.builder().name("R1").build();
        when(reservationServiceMock.getById(anyLong())).thenReturn(r1);

        mockMvc.perform(get("/api/v1/reservations/{reservationId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is("R1")));

        verify(reservationServiceMock, times(1)).getById(anyLong());
        verifyNoMoreInteractions(reservationServiceMock);
    }

    @Test
    public void testReservationGetByRoomId() throws Exception {
        Reservation r1 = Reservation.builder().name("R1").room(Room.builder().id(5l).build()).build();
        Reservation r2 = Reservation.builder().name("R2").room(Room.builder().id(5l).build()).build();
        when(reservationServiceMock.getByRoom(anyLong())).thenReturn(Arrays.asList(r1, r2));

        mockMvc.perform(get("/api/v1/reservations").param("roomId", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("R1")))
                .andExpect(jsonPath("$[1].name", is("R2")));

        verify(reservationServiceMock, times(1)).getByRoom(anyLong());
        verifyNoMoreInteractions(reservationServiceMock);
    }

    @Test
    public void testReservationCreate() throws Exception {
        ReservationDTO newReservation =
                ReservationDTO.builder().name("NewReservation").numberOfPeople(2).startDate(LocalDate.now()).endDate(LocalDate.now().plus(2, ChronoUnit.DAYS)).build();
        when(reservationServiceMock.add(any(ReservationDTO.class))).thenReturn(Reservation.fromDTO(newReservation));

        mockMvc.perform(post("/api/v1/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(newReservation)))
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NewReservation")));
        ArgumentCaptor<ReservationDTO> resCaptor = ArgumentCaptor.forClass(ReservationDTO.class);
        verify(reservationServiceMock, times(1)).add(resCaptor.capture());
        verifyNoMoreInteractions(reservationServiceMock);
    }

    @Test
    public void testReservationDelete() throws Exception {
        ReservationDTO toDelete =
                ReservationDTO.builder().name("DELETE").numberOfPeople(2).startDate(LocalDate.now()).endDate(LocalDate.now().plus(2,
                        ChronoUnit.DAYS)).build();

        doNothing().when(reservationServiceMock).delete((any(ReservationDTO.class)));

        mockMvc.perform(delete("/api/v1/reservations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(toDelete)))
                .andExpect(status().isOk());

        ArgumentCaptor<ReservationDTO> resCaptor = ArgumentCaptor.forClass(ReservationDTO.class);
        verify(reservationServiceMock, times(1)).delete(resCaptor.capture());
        verifyNoMoreInteractions(reservationServiceMock);
    }
}
