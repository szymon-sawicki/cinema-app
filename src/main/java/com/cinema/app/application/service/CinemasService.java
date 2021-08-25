package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.AddressUtils;
import com.cinema.app.domain.cinema.CinemaUtils;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema.dto.validator.CreateCinemaDtoValidator;
import com.cinema.app.domain.cinema_room.CinemaRoom;
import com.cinema.app.domain.cinema_room.dto.CreateCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.GetCinemaRoomDto;
import com.cinema.app.domain.cinema_room.dto.validator.CreateCinemaRoomDtoValidator;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.CinemaDao;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemasService {

    private final CinemaDao cinemaDao;
    private final CinemaRoomDao cinemaRoomDao;
    private final AddressDao addressDao;

    public GetCinemaDto addCinema(CreateCinemaDto createCinemaDto) {
        Validator.validate(new CreateCinemaDtoValidator(), createCinemaDto);

        var addressDto = createCinemaDto.getCreateAddressDto();
        var cinemaRoomDtos = createCinemaDto.getCinemaRoomDtos();
        var address = addressDto.toAddress();

        var addressFromDb = addressDao
                .findAddress(
                        addressDto.getStreet(),
                        addressDto.getHouseNumber(),
                        addressDto.getCity(),
                        addressDto.getZipCode())
                .orElseGet(() -> addressDao
                        .save(address)
                        .orElseThrow(() -> new CinemaServiceException("Cannot add new address")));

        var cinemaToInsert = createCinemaDto
                .toCinema()
                .withAddress(AddressUtils.toId.apply(addressFromDb));

        var cinemaFromDb = cinemaDao
                .save(cinemaToInsert)
                .orElseThrow(() -> new CinemaServiceException("Cannot add new cinema"));

        if(cinemaRoomDtos != null || !cinemaRoomDtos.isEmpty()) {
            addCinemaRoomsToCinema(CinemaUtils.toId.apply(cinemaFromDb),cinemaRoomDtos);
        }

        return cinemaFromDb.toGetCinemaDto();

    }

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

        cinemaRooms.forEach(cinemaRoom -> Validator.validate(new CreateCinemaRoomDtoValidator(),cinemaRoom));

        var cinemaRoomsToInsert = cinemaRooms
                .stream()
                .map(createCinemaRoomDto -> createCinemaRoomDto
                        .toCinemaRoom()
                        .withCinemaId(cinemaId)
                ).collect(Collectors.toList());
       return cinemaRoomDao.saveAll(cinemaRoomsToInsert).stream().map(CinemaRoom::toGetCinemaRoomDto).toList();

    }

    public List<GetCinemaDto> findByCity(String city) {
        if (city == null) {
            throw new CinemaServiceException("city is null");
        }
        if(!city.matches("[\\w\\s\\-]{3,30}+")) {
        throw new CinemaServiceException("city have wrong format");
    }
        return addressDao.findAllFromCity(city).stream()
                .map(address -> cinemaDao
                        .findByAddress(AddressUtils.toId.apply(address))
                        .orElseThrow(() -> new CinemaServiceException("cannot find element"))
                        .toGetCinemaDto())
                .toList();
    }

    public GetCinemaDto findByName(String name) {
        if (name == null) {
            throw new CinemaServiceException("name is null");
        }
        if(!name.matches("[\\w\\s\\-]{3,30}+")) {
            throw new CinemaServiceException("name have wrong format");
        }
        return cinemaDao
                .findByName(name)
                .orElseThrow(() -> new CinemaServiceException("Cannot find cinema with name: " + name))
                .toGetCinemaDto();
    }

}
