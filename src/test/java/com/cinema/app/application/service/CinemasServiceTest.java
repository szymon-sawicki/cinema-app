package com.cinema.app.application.service;

import com.cinema.app.infrastructure.persistence.AddressDao;
import com.cinema.app.infrastructure.persistence.CinemaDao;
import com.cinema.app.infrastructure.persistence.CinemaRoomDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CinemasServiceTest {

    @Mock
    private CinemaDao cinemaDao;
    @Mock
    private AddressDao addressDao;
    @Mock
    private CinemaRoomDao cinemaRoomDao;

    @InjectMocks
    private CinemasService cinemasService;



}
