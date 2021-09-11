package com.cinema.app.infrastructure.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class to transform data used in web api (spark framework)
 *
 * @author Szymon Sawicki
 */

public class JsonTransformer implements ResponseTransformer {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .setPrettyPrinting().create();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }

    // TODO zrobić bean?
}

//