package com.cinema.app.domain.screening;

import java.util.function.Function;

public interface ScreeningUtils {
    Function<Screening,Long> toId = screening -> screening.id;
}
