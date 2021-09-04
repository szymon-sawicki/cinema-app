package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.AddressUtils;
import com.cinema.app.domain.cinema.CinemaUtils;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema.dto.validator.CreateCinemaDtoValidator;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.validator.CreateCinemaRoomDtoValidator;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import com.cinema.app.infrastructure.persistence.dao.AddressEntityDao;
import com.cinema.app.infrastructure.persistence.dao.CinemaEntityDao;
import com.cinema.app.infrastructure.persistence.dao.CinemaRoomEntityDao;
import com.cinema.app.infrastructure.persistence.dao.SeatEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

/**
 * service class used to managing cinemas. Data is fetched from 4 different daos
 * @Author Szymon Sawicki
 */

public class CinemasService {

    private final CinemaEntityDao cinemaEntityDao;
    private final CinemaRoomEntityDao cinemaRoomEntityDao;
    private final AddressEntityDao addressEntityDao;
    private final SeatEntityDao seatEntityDao;


    /**
     * Method used to create new cinema. Dto contains name, address and list of cinema rooms.
     * If address not exists in database, will be created. All cinema rooms and seat will be added to their tables  in db.
     * @param createCinemaDto cinema to be created.
     * @return inserted cinema
     */

    public GetCinemaDto addCinema(CreateCinemaDto createCinemaDto) {
        Validator.validate(new CreateCinemaDtoValidator(), createCinemaDto);

        var addressDto = createCinemaDto.getCreateAddressDto();
        var cinemaRoomDtos = createCinemaDto.getCinemaRoomDtos();
        var address = addressDto.toAddress().toEntity();

        var addressFromDb = addressEntityDao
                .findAddress(
                        addressDto.getStreet(),
                        addressDto.getHouseNumber(),
                        addressDto.getCity(),
                        addressDto.getZipCode())
                .orElseGet(() -> addressEntityDao
                        .save(address)
                        .orElseThrow(() -> new CinemaServiceException("Cannot add new address")));


        var cinemaToInsert = createCinemaDto
                .toCinema()
                .withAddress(AddressUtils.toId.apply(addressFromDb.toAddress()))
                .toEntity();



        var cinemaFromDb = cinemaEntityDao
                .save(cinemaToInsert)
                .orElseThrow(() -> new CinemaServiceException("Cannot add new cinema"));

        if(cinemaRoomDtos != null || !cinemaRoomDtos.isEmpty()) {
            addCinemaRoomsToCinema(CinemaUtils.toId.apply(cinemaFromDb.toCinema()),cinemaRoomDtos);
        }

        return cinemaFromDb.toCinema().toGetCinemaDto();

    }

    /**
     *
     * @param cinemaId in that cinema new rooms will be created
     * @param cinemaRooms rooms to add
     * @return aded cinema rooms
     */

    public List<GetCinemaRoomDto> addCinemaRoomsToCinema(Long cinemaId, List<CreateCinemaRoomDto> cinemaRooms) {
        if(cinemaId == null) {
            throw new CinemaServiceException("cinema id is null");
        }
        if(cinemaId <= 0) {
            throw new CinemaServiceException("cinema id is lequal or lower than 0");
        }
        if(cinemaRooms == null) {
            throw new CinemaServiceException("cinema rooms list is null");
        }
        if(cinemaRooms.isEmpty()) {
            throw new CinemaServiceException("cinema rooms list is empty");
        }

        cinemaRooms.forEach(cinemaRoom -> {
            Validator.validate(new CreateCinemaRoomDtoValidator(),cinemaRoom);
        });

        var cinemaRoomsToInsert = cinemaRooms
                .stream()
                .map(createCinemaRoomDto -> createCinemaRoomDto
                        .toCinemaRoom()
                        .withCinemaId(cinemaId)
                        .toEntity()
                ).collect(Collectors.toList());

       var result = cinemaRoomEntityDao.saveAll(cinemaRoomsToInsert).stream().map(cinemaRoom -> cinemaRoom.toCinemaRoom().toGetCinemaRoomDto()).toList();

       result.forEach(this::addSeatsToCinemaRoom);

       return result;

    }

    private void addSeatsToCinemaRoom(GetCinemaRoomDto getCinemaRoomDto) {

        for (int row = 1; row < getCinemaRoomDto.getRowsNum()+1;row++) {
            for (int place = 1; place < getCinemaRoomDto.getPlaceNumber()+1; place++) {
                var seat = Seat.builder()
                        .cinemaRoomId(getCinemaRoomDto.getId())
                        .seatType(SeatType.CHAIR)
                        .rowNum(row)
                        .place(place)
                        .build();
                seatEntityDao.save(seat.toEntity());
            }
        }

    }

    /**
     *
     * @param city
     * @return list of cinemas in taht city
     */

    public List<GetCinemaDto> findByCity(String city) {
        if (city == null) {
            throw new CinemaServiceException("city is null");
        }
        if(!city.matches("[\\w\\s\\-]{3,30}+")) {
        throw new CinemaServiceException("city have wrong format");
    }
        return addressEntityDao.findAllIdsFromCity(city).stream()
                .map(addressId -> cinemaEntityDao
                        .findByAddress(addressId)
                        .orElseThrow(() -> new CinemaServiceException("cannot find element"))
                        .toCinema()
                        .toGetCinemaDto())
                .toList();
    }

    /**
     *
     * @param name name of cinema ro search
     * @return cinema with that name
     */

    public GetCinemaDto findByName(String name) {
        if (name == null) {
            throw new CinemaServiceException("name is null");
        }
        if(!name.matches("[\\w\\s\\-]{3,30}+")) {
            throw new CinemaServiceException("name have wrong format");
        }
        return cinemaEntityDao
                .findByName(name)
                .orElseThrow(() -> new CinemaServiceException("Cannot find cinema with name: " + name))
                .toCinema()
                .toGetCinemaDto();
    }

}
