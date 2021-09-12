package com.cinema.app.infrastructure.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spark.ResponseTransformer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class to transform data used in web api (spark framework)
 *
 * @author Szymon Sawicki
 */
@RequiredArgsConstructor
@Component

public class JsonTransformer implements ResponseTransformer {

    private final Gson gson;

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}

//