package com.cinema.app.domain.user;

import java.time.LocalDate;
import java.util.function.Function;

public interface UserUtils {
    Function<User, Long> toId = user -> user.id;
    Function<User, String> toPassword = user -> user.password;
    Function<User, LocalDate> toCreationDate = user -> user.creationDate;
}
