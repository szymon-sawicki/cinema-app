package com.cinema.app.application.service;

import com.cinema.app.infrastructure.persistence.dao.ScreeningEntityDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ScreeningsServiceTest {

    @Mock
    private ScreeningEntityDao screeningEntityDao;

    @InjectMocks
    private ScreeningsService screeningsService;

    @Test
    @DisplayName("when time of screening is already booked")
    public void test1() {


    }
}
