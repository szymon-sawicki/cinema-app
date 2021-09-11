package com.cinema.app.infrastructure.routing;

import com.cinema.app.application.service.CinemasService;
import com.cinema.app.application.service.exception.CinemaServiceException;
import com.cinema.app.domain.cinema.dto.CreateUpdateCinemaDto;
import com.cinema.app.infrastructure.configs.JsonTransformer;
import com.cinema.app.infrastructure.routing.dto.ResponseDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static com.cinema.app.infrastructure.routing.dto.ResponseDto.*;
import static spark.Spark.*;

@RequiredArgsConstructor
@Controller

public class CinemaRouting {

    private final CinemasService cinemasService;
    private final Gson gson;


    public void routes() {


        exception(CinemaServiceException.class, (exception, request, response) -> {
            response.redirect("/error/" + exception.getMessage(), 301);
        });

        path("/cinemas", () -> {

                    post("",
                            (request, response) -> {
                                var createCinemaDto = gson.fromJson(request.body(), CreateUpdateCinemaDto.class);
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return toResponse(cinemasService.addCinema(createCinemaDto));
                                }, new JsonTransformer());

                    get("/city/:city",
                            ((request, response) -> {
                                var city = request.params(":city");
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return toResponse(cinemasService.findByCity(city));
                            }
                            ), new JsonTransformer());

                    get("/name/:name",
                            ((request, response) -> {
                                var name = request.params(":name");
                                response.header("Content-Type", "application/json;charset=utf-8");
                                return toResponse(cinemasService.findByName(name));
                            }),
                            new JsonTransformer());
                });
    }
    }
