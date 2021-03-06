package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.AddressUtils;
import com.cinema.app.domain.cinema.CinemaUtils;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaInfoDto;
import com.cinema.app.domain.cinema.dto.validator.CreateUpdateCinemaDtoValidator;
import com.cinema.app.domain.cinema_room.dto.CreateUpdateCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.validator.CreateCinemaRoomDtoValidator;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.domain.seat.Seat;
import com.cinema.app.domain.seat.type.SeatType;
import com.cinema.app.infrastructure.persistence.dao.*;
import com.cinema.app.infrastructure.persistence.entity.view.CinemaInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    private final ScreeningEntityDao screeningEntityDao;
    private final CinemaInfoDao cinemaInfoDao;


    /**
     * Method used to create new cinema. Dto contains name, address and list of cinema rooms.
     * If address not exists in database, will be created. All cinema rooms and seat will be added to their tables  in db.
     * @param createUpdateCinemaDto cinema to be created.
     * @return inserted cinema
     */

    public GetCinemaDto addCinema(CreateUpdateCinemaDto createUpdateCinemaDto) {
        Validator.validate(new CreateUpdateCinemaDtoValidator(), createUpdateCinemaDto);

        var addressDto = createUpdateCinemaDto.getCreateAddressDto();
        var cinemaRoomDtos = createUpdateCinemaDto.getCinemaRoomDtos();
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


        var cinemaToInsert = createUpdateCinemaDto
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

    public List<GetCinemaRoomDto> addCinemaRoomsToCinema(Long cinemaId, List<CreateUpdateCinemaRoomDto> cinemaRooms) {
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

    /**
     * method finding all cinemas in db
     * @return list with all cinemas
     */


    public List<GetCinemaDto> findAll() {
        return cinemaEntityDao.findAll()
                .stream()
                .map(cinemaEntity -> cinemaEntity.toCinema().toGetCinemaDto())
                .toList();
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
     * @param city to be search
     * @return list of cinemas in that city
     */

    public List<GetCinemaInfoDto> findByCity(String city) {
        if (city == null) {
            throw new CinemaServiceException("city is null");
        }
        if(!city.matches("[\\w\\s\\-]{3,30}+")) {
        throw new CinemaServiceException("city have wrong format");
    }
        var result = cinemaInfoDao.findByCity(city)
                .stream()
                .map(CinemaInfo::toGetCinemaInfoDto)
                .toList();

        if(result.isEmpty()) {
            throw new CinemaServiceException("cannot find any cinemas in given city");
        }

        return result;
    }

    /**
     *
     * @param name name of cinema to search
     * @return cinema with that name
     */

    public GetCinemaInfoDto findByName(String name) {
        if (name == null) {
            throw new CinemaServiceException("name is null");
        }
        if(!name.matches("[\\w\\s\\-]{3,30}+")) {
            throw new CinemaServiceException("name have wrong format");
        }
        return cinemaInfoDao.findByName(name)
                .orElseThrow(() -> new CinemaServiceException("cannot find cinema by name"))
                .toGetCinemaInfoDto();
    }

    /**
     * method deleting cinema with all cinema rooms and seats
     * @param cinemaId cinema to be deleted
     * @return deleted cinema
     */

   /* public GetCinemaDto deleteCinema(Long cinemaId) {
        if(cinemaId == null) {
            throw new CinemaServiceException("id is null");
        }
        if(cinemaId <= 0) {
            throw new CinemaServiceException("id is 0 or negative");
        }

        var cinemaRoomsIds = cinemaRoomEntityDao.findAllIdsFromCinema(cinemaId);


        cinemaRoomsIds.forEach(cinemaRoomId->{
            var seatIdsToDelete = seatEntityDao.findIdsByCinemaRoom(cinemaRoomId);
            seatEntityDao.deleteAllById(seatIdsToDelete);
            var screeningsIdsToDelete = screeningEntityDao.findAllIdsByCinemaRoom(cinemaRoomId);
                screeningEntityDao.deleteAllById(screeningsIdsToDelete);

        });

        cinemaRoomEntityDao.deleteAllById(cinemaRoomsIds);

        return cinemaEntityDao.deleteById(cinemaId)
                .orElseThrow(() -> new CinemaServiceException("cannot delete cinema"))
                .toCinema()
                .toGetCinemaDto();

    }*/

}
