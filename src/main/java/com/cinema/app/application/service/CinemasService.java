package com.cinema.app.application.service;

import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.address.AddressUtils;
import com.cinema.app.domain.cinema.CinemaUtils;
import com.cinema.app.domain.cinema.dto.CreateCinemaDto;
import com.cinema.app.domain.cinema.dto.GetCinemaDto;
import com.cinema.app.domain.cinema.dto.validator.CreateCinemaDtoValidator;
import com.cinema.app.domain.cinema_room.CinemaRoomUtils;
import com.cinema.app.domain.configs.validator.Validator;
import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.CinemaDao;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        var cinemaRoomsToInsert = createCinemaDto.getCinemaRoomDtos()
                .stream()
                .map(createCinemaRoomDto -> createCinemaRoomDto
                            .toCinemaRoom()
                            .withCinemaId(CinemaUtils.toId.apply(cinemaFromDb))
                ).collect(Collectors.toList());
        cinemaRoomDao.saveAll(cinemaRoomsToInsert);

        return cinemaFromDb.toGetCinemaDto();

    }

    // TODO
    // Wyszukiwanie kina po nazwie miejscowosci
    // Wyszukiwanie kina po nazwie kina
}
