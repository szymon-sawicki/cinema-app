package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.application.service.exception.TicketsServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.dto.CreateTicketDto;
import com.cinema.app.domain.ticket.dto.GetTicketDto;
import com.cinema.app.domain.ticket.dto.validator.CreateTicketDtoValidator;
import com.cinema.app.domain.user.UserUtils;
import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import com.cinema.app.infrastructure.persistence.dao.SeatEntityDao;
import com.cinema.app.infrastructure.persistence.dao.TicketEntityDao;
import com.cinema.app.infrastructure.persistence.dao.UserEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TicketsService {

    private final TicketEntityDao ticketEntityDao;
    private final ScreeningEntityDao screeningEntityDao;
    private final SeatEntityDao seatEntityDao;
    private final UserEntityDao userEntityDao;

    public GetTicketDto createTicket(CreateTicketDto createTicketDto) {
        Validator.validate(new CreateTicketDtoValidator(), createTicketDto);

        var createUserDto = createTicketDto.getCreateUserDto();
        var user = createUserDto.toUser().toEntity();

        var userFromDb = userEntityDao.findByUsername(createUserDto.getUsername())
                .orElseGet(() -> userEntityDao
                        .save(user.toUser().withCreationDateToday().toEntity())
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new user")));

        var getScreeningDto = screeningEntityDao.findById(createTicketDto.getScreeningId())
                .orElseThrow(() -> new ScreeningsServiceException("cannot find screening"))
                .toScreening()
                .toGetScreeningDto();

        // TODO Jeden user moze sobie zaznaczyc kilka miejsc ale robi sie z tego kilka biletow
        // kazdy bilet na jedno miejsce
        var getSeatDto = seatEntityDao.findById(createTicketDto.getSeatId())
                .orElseThrow(() -> new ScreeningsServiceException("cannot find seat"))
                .toSeat()
                .toGetSeatDto();

        var seatWithBooking = getSeatsOfScreening(getScreeningDto).stream()
                .filter(seat -> seat.getId().compareTo(getSeatDto.getId()) == 0)
                .findFirst()
                .orElseThrow(() -> new CinemaServiceException("cannot find seat"));

        if (seatWithBooking.isBooked()) {
            throw new CinemaServiceException("seat is already booked");
        }

        var ticketEntity = createTicketDto
                .toTicket()
                .withUserId(UserUtils.toId.apply(userFromDb.toUser()))
                .toEntity();

        return ticketEntityDao.save(ticketEntity)
                .orElseThrow(() -> new TicketsServiceException("cannot add new ticket"))
                .toTicket()
                .toGetTicketDto();

    }

    // TODO zrobic mape

    public List<GetSeatDto> getSeatsOfScreening(GetScreeningDto getScreeningDto) {
        if (getScreeningDto == null) {
            throw new TicketsServiceException("get screening dto is null");
        }

        var seatsFromDb = seatEntityDao.findSeatsByCinemaRoom(getScreeningDto.getCinemaRoomId());
        var ticketsOfScreening = ticketEntityDao.findAllByScreeningId(getScreeningDto.getId());

        var bookedSeatIds = ticketsOfScreening
                .stream()
                .map(ticketEntity -> ticketEntity.toTicket().toGetTicketDto().getSeatId())
                .toList();

        return seatsFromDb.stream().map(seat -> {
                    var getSeatDto = seat.toSeat().toGetSeatDto();
                    if (bookedSeatIds.contains(getSeatDto.getId())) {
                        getSeatDto.setBooked(true);
                    }
                    return getSeatDto;
                })
                .toList();

    }
}
