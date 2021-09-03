package com.cinema.app.infrastructure.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

/**
 * Class to transform data used in web api (spark framework)
 * @author Szymon Sawicki
 */

public class JsonTransformer implements ResponseTransformer {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
