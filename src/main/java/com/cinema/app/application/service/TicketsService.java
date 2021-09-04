package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.ScreeningsServiceException;
import com.cinema.app.application.service.exception.TicketsServiceException;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.screening.dto.GetScreeningDto;
import com.cinema.app.domain.seat.dto.GetSeatDto;
import com.cinema.app.domain.ticket.dto.CreateUpdateTicketDto;
import com.cinema.app.domain.ticket.dto.GetTicketDto;
import com.cinema.app.domain.ticket.dto.validator.CreateUpdateTicketDtoValidator;
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
     *
     * @param createUpdateTicketDto ticket to create
     * @return list of created tickets
     */

    public List<GetTicketDto> createTickets(CreateUpdateTicketDto createUpdateTicketDto) {
        Validator.validate(new CreateUpdateTicketDtoValidator(), createUpdateTicketDto);

        var createUserDto = createUpdateTicketDto.getCreateUserDto();
        var user = createUserDto.toUser().toEntity();

        // if user with given username exists, data will be fetched from db, otherwise new user will be created

        var userFromDb = userEntityDao.findByUsername(createUserDto.getUsername())
                .orElseGet(() -> userEntityDao
                        .save(user.toUser().withCreationDateToday().toEntity())
                        .orElseThrow(() -> new ScreeningsServiceException("cannot add new user")));

        var userId = userFromDb.toUser().toGetUserDto().getId();
        var screeningId = createUpdateTicketDto.getScreeningId();

        var getScreeningDto = screeningEntityDao.findById(screeningId)
                .orElseThrow(() -> new ScreeningsServiceException("cannot find screening"))
                .toScreening()
                .toGetScreeningDto();

        // checking availability of all seats from create dto and returning list with checked seats

        var ticketsToInsert = checkSeatAndGenerateTickets(createUpdateTicketDto.getSeats(), getScreeningDto)
                .stream()
                .map(seat -> createUpdateTicketDto.toTicket()
                        .withSeatId(seat.getId())
                        .withUserId(userId).toEntity())
                .toList();


        return ticketEntityDao.saveAll(ticketsToInsert)
                .stream().map(ticketEntity -> ticketEntity.toTicket().toGetTicketDto())
                .toList();

    }
    // private method to check availability of all seats in ticket

    private List<GetSeatDto> checkSeatAndGenerateTickets(List<GetSeatDto> seatsToBook, GetScreeningDto getScreeningDto) {

        var bookingsMap = mapSeatsOfScreening(getScreeningDto);
        var result = new ArrayList<TicketEntity>();

        seatsToBook.forEach(seat -> {
            var seatId = seat.getId();
            if (bookingsMap.get(seatId)) {
                throw new TicketsServiceException("seat is already booked");
            }
        });

        return seatsToBook;

    }

    /**
     * @param getScreeningDto screening to be checked
     * @return map with get seat dto as key and boolean as value. True - seat is already booked, false - seat is not booked
     */

    public Map<Long, Boolean> mapSeatsOfScreening(GetScreeningDto getScreeningDto) {
        if (getScreeningDto == null) {
            throw new TicketsServiceException("get screening dto is null");
        }

        return seatEntityDao.findSeatsByCinemaRoom(getScreeningDto.getCinemaRoomId())
                .stream()
                .map(seat -> seat.toSeat().toGetSeatDto())
                .collect(Collectors.toMap(seat -> seat.getId(), getSeatDto -> {
                    return ticketEntityDao.findByScreeningAndSeat(getScreeningDto.getId(), getSeatDto.getId()).isPresent();
                }));
    }

}

