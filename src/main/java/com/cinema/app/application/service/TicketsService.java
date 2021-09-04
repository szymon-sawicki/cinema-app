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
import com.cinema.app.infrastructure.persistence.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor


public class TicketsService {

    private final TicketEntityDao ticketEntityDao;
    private final ScreeningEntityDao screeningEntityDao;
    private final SeatEntityDao seatEntityDao;
    private final UserEntityDao userEntityDao;

    /**
     * method used to create new tickets. Each transaction can contain many seat to book.
     * Availability of each seat will be checked, new ticket will be generated and added to database
     * @param createTicketDto ticket to create
     * @return list of created tickets
     */

    public List<GetTicketDto> createTickets(CreateTicketDto createTicketDto) {
        Validator.validate(new CreateTicketDtoValidator(), createTicketDto);

        var createUserDto = createTicketDto.getCreateUserDto();
        var user = createUserDto.toUser().toEntity();

        // if user with given username exists, data will be fetched from db, otherwise new user will be created

        var userFromDb = userEntityDao.findByUsername(createUserDto.getUsername())
                .orElseGet(() -> userEntityDao
                        .save(user.toUser().withCreationDateToday().toEntity())
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new user")));

        var userId = userFromDb.toUser().toGetUserDto().getId();

        var getScreeningDto = screeningEntityDao.findById(createTicketDto.getScreeningId())
                .orElseThrow(() -> new ScreeningsServiceException("cannot find screening"))
                .toScreening()
                .toGetScreeningDto();

        var bookingsMap = mapSeatsOfScreening(getScreeningDto);
        var seatsToBook = createTicketDto.getSeats();

        seatsToBook.forEach(seat -> {
            if (bookingsMap.containsKey(seat) && bookingsMap.get(seat)) {
                throw new TicketsServiceException("seat is already booked");
            }
            bookingsMap.put(seat, true);
            var ticketEntity = createTicketDto.toTicket().withSeatId(seat.getId()).withUserId(userId).toEntity();
            ticketEntityDao.save(ticketEntity)
                    .orElseThrow(() -> new TicketsServiceException("cannot add new ticket"));
        });

        return seatsToBook.stream()
                .map(seatToBook -> createTicketDto
                        .toTicket()
                        .withSeatId(seatToBook.getId())
                        .toGetTicketDto())
                .toList();

    }

    /**
     * @param getScreeningDto screening to be checked
     * @return map with get seat dto as key and boolean as value. True - seat is already booked, false - seat is not booked
     */

    public Map<GetSeatDto, Boolean> mapSeatsOfScreening(GetScreeningDto getScreeningDto) {
        if (getScreeningDto == null) {
            throw new TicketsServiceException("get screening dto is null");
        }

        return seatEntityDao.findSeatsByCinemaRoom(getScreeningDto.getCinemaRoomId())
                .stream()
                .map(seat -> seat.toSeat().toGetSeatDto())
                .collect(Collectors.toMap(Function.identity(), getSeatDto -> {
                    var result = ticketEntityDao.findByScreeningAndSeat(getScreeningDto.getId(), getSeatDto.getId()).isPresent();
                    return result;
                }));
    }
}
